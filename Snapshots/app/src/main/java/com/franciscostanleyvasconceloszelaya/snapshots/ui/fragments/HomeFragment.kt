package com.franciscostanleyvasconceloszelaya.snapshots.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.franciscostanleyvasconceloszelaya.snapshots.databinding.FragmentHomeBinding
import com.franciscostanleyvasconceloszelaya.snapshots.databinding.ItemSnapshotBinding
import com.google.firebase.database.FirebaseDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import com.franciscostanleyvasconceloszelaya.snapshots.R
import com.franciscostanleyvasconceloszelaya.snapshots.SnapshotsApplication
import com.franciscostanleyvasconceloszelaya.snapshots.entities.Snapshot
import com.franciscostanleyvasconceloszelaya.snapshots.utils.FragmentAux
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment(), FragmentAux {

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mFireBaseAdapter: FirebaseRecyclerAdapter<Snapshot, SnapshotHolder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mSnapshotsRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFireBase()
        setUpAdapter()
        setUpRecyclerView()
    }


    private fun setUpAdapter() {
        val query = mSnapshotsRef
        val options = FirebaseRecyclerOptions.Builder<Snapshot>().setQuery(query) {
            val snapshot = it.getValue(Snapshot::class.java)
            snapshot!!.id = it.key!!
            snapshot
        }.build()

        mFireBaseAdapter = object : FirebaseRecyclerAdapter<Snapshot, SnapshotHolder>(options) {
            private lateinit var mContext: Context
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapshotHolder {
                mContext = parent.context

                val view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_snapshot, parent, false)
                return SnapshotHolder(view)
            }

            override fun onBindViewHolder(holder: SnapshotHolder, position: Int, model: Snapshot) {
                val snapshot = getItem(position)

                with(holder) {
                    setListener(snapshot)

                    with(binding) {
                        tvTitle.text = snapshot.title
                        cbLike.text = snapshot.likeList.keys.size.toString()
                        cbLike.isChecked =
                            snapshot.likeList.containsKey(SnapshotsApplication.currentUser.uid)

                        Glide.with(mContext)
                            .load(snapshot.photoUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(imgPhoto)
                    }
                }
            }

            @SuppressLint("NotifyDataSetChanged") //internal error of firebase ui 8.0.0
            override fun onDataChanged() {
                super.onDataChanged()
                mBinding.progressBar.visibility = View.GONE
                notifyDataSetChanged()
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Snackbar.make(mBinding.root, error.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpFireBase() {
        mSnapshotsRef =
            FirebaseDatabase.getInstance().reference.child(SnapshotsApplication.PATH_SNAPSHOTS)
    }

    private fun setUpRecyclerView() {
        mLayoutManager = LinearLayoutManager(context)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFireBaseAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        mFireBaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFireBaseAdapter.stopListening()
    }

    private fun deleteSnapshot(snapshot: Snapshot) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.dialog_delete_title)
                .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                    val storageSnapshotsRef = FirebaseStorage.getInstance().reference
                        .child(SnapshotsApplication.PATH_SNAPSHOTS)
                        .child(SnapshotsApplication.currentUser.uid)
                        .child(snapshot.id)
                    storageSnapshotsRef.delete().addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            mSnapshotsRef.child(snapshot.id).removeValue()
                        } else {
                            Snackbar.make(
                                mBinding.root, getString(R.string.home_delete_photo_error),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                .setNegativeButton(R.string.dialog_delete_cancel, null)
                .show()
        }
    }

    private fun setLike(snapshot: Snapshot, checked: Boolean) {
        val myUserRef = mSnapshotsRef.child(snapshot.id)
            .child(SnapshotsApplication.PROPERTY_LIKE_LIST)
            .child(SnapshotsApplication.currentUser.uid)
        if (checked) {
            myUserRef.setValue(checked)
        } else {
            myUserRef.setValue(null)
        }
    }

    override fun refresh() {
        mBinding.recyclerView.smoothScrollToPosition(0)
    }

    inner class SnapshotHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSnapshotBinding.bind(view)

        @SuppressLint("SyntheticAccessor")
        fun setListener(snapshot: Snapshot) {
            with(binding) {
                btnDelete.setOnClickListener { deleteSnapshot(snapshot) }
                cbLike.setOnCheckedChangeListener { _, checked ->
                    setLike(snapshot, checked)
                }
            }
        }
    }
}