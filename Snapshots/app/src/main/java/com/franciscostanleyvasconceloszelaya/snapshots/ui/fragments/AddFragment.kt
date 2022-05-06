package com.franciscostanleyvasconceloszelaya.snapshots.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.franciscostanleyvasconceloszelaya.snapshots.R
import com.franciscostanleyvasconceloszelaya.snapshots.entities.Snapshot
import com.franciscostanleyvasconceloszelaya.snapshots.databinding.FragmentAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddFragment : Fragment() {
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference

    private lateinit var mBinding: FragmentAddBinding

    private var mPhotoSelectedUri: Uri? = null

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                mPhotoSelectedUri = it?.data?.data
                with(mBinding) {
                    imgPhoto.setImageURI(mPhotoSelectedUri)
                    tilTitle.visibility = View.VISIBLE
                    tvMessage.text = getString(R.string.post_message_valid_title)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAddBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnPost.setOnClickListener { postSnapshot() }

        mBinding.btnSelect.setOnClickListener { openGallery() }

        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference =
            FirebaseDatabase.getInstance().reference.child("snapshots")
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResult.launch(intent)
    }

    private fun postSnapshot() {
        mBinding.progressBar.visibility = View.VISIBLE
        val key = mDatabaseReference.push().key!!
        val storageReference = mStorageReference.child("snapshots")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(key)
        if (mPhotoSelectedUri != null) {
            storageReference.putFile(mPhotoSelectedUri!!)
                .addOnProgressListener {
                    val progress = (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                    mBinding.progressBar.progress = progress.toInt()
                    mBinding.tvMessage.text = String.format("%s%%", progress)
                }
                .addOnCompleteListener {
                    mBinding.progressBar.visibility = View.INVISIBLE
                }
                .addOnSuccessListener { it ->
                    Toast.makeText(context, "Posted Snapshot", Toast.LENGTH_SHORT)
                        .show()
                    it.storage.downloadUrl.addOnSuccessListener {
                        saveSnapshot(key, it.toString(), mBinding.etTitle.text.toString().trim())
                        mBinding.tilTitle.visibility = View.GONE
                        mBinding.tvMessage.text = getString(R.string.post_message_title)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Can't upload, try again later", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun saveSnapshot(key: String, url: String, title: String) {
        val snapshot = Snapshot(title = title, photoUrl = url)
        mDatabaseReference.child(key).setValue(snapshot)
    }


}