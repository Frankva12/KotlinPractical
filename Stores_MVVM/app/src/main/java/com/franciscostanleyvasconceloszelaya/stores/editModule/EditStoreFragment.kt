package com.franciscostanleyvasconceloszelaya.stores.editModule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.franciscostanleyvasconceloszelaya.stores.R
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.databinding.FragmentEditStoreBinding
import com.franciscostanleyvasconceloszelaya.stores.editModule.viewModel.EditStoreViewModel
import com.franciscostanleyvasconceloszelaya.stores.mainModule.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class EditStoreFragment : Fragment() {

    private lateinit var mBinding: FragmentEditStoreBinding

    //MVVM
    private lateinit var mEditStoreViewModel: EditStoreViewModel
    private lateinit var mStoreEntity: StoreEntity

    private var mActivity: MainActivity? = null
    private var mIsEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEditStoreViewModel =
            ViewModelProvider(requireActivity())[EditStoreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEditStoreBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //MVVM
        setUpViewModel()

        setUpTextFields()
    }

    private fun setUpViewModel() {
        mEditStoreViewModel.getStoreSelected().observe(viewLifecycleOwner) {
            mStoreEntity = it ?: StoreEntity()
            if (it != null) {
                mIsEditMode = true
                setUiStore(it)
            } else {
                mIsEditMode = false
            }

            setUpActionBar()
        }

        mEditStoreViewModel.getResult().observe(viewLifecycleOwner) { result ->
            hideKeyBoard()
            when (result) {
                is Long -> {
                    mStoreEntity.id = mStoreEntity.id
                    mEditStoreViewModel.setStoreSelected(mStoreEntity)
                    Toast.makeText(
                        mActivity,
                        R.string.edit_store_message_save_success,
                        Toast.LENGTH_SHORT
                    ).show()

                    mActivity?.onBackPressed()
                }
                is StoreEntity -> {
                    mEditStoreViewModel.setStoreSelected(mStoreEntity)
                    Snackbar.make(
                        mBinding.root,
                        R.string.edit_store_message_update_success,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun setUpActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = if (mIsEditMode) {
            getString(R.string.edit_store_title_add)
        } else {
            getString(R.string.edit_store_title_edit)
        }

        setHasOptionsMenu(true)
    }

    private fun setUpTextFields() {
        with(mBinding) {
            etPhotoUrl.addTextChangedListener {
                loadImage(it.toString().trim())
                validateFields(tilPhotoUrl)
            }

            etName.addTextChangedListener { validateFields(tilName) }
            etPhone.addTextChangedListener { validateFields(tilPhone) }
        }

    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.imgPhoto)
    }

    private fun setUiStore(storeEntity: StoreEntity) {
        with(mBinding) {
            etName.text = storeEntity.name.editable()
            etPhone.text = storeEntity.phone.editable()
            etWebsite.text = storeEntity.website.editable()
            etPhotoUrl.text = storeEntity.photoUrl.editable()
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true

            }
            R.id.action_save -> {
                if (validateFields(
                        mBinding.tilPhotoUrl,
                        mBinding.tilPhone,
                        mBinding.tilName
                    )
                ) {
                    with(mStoreEntity) {
                        name = mBinding.etName.text.toString().trim()
                        phone = mBinding.etPhone.text.toString().trim()
                        website = mBinding.etWebsite.text.toString().trim()
                        photoUrl = mBinding.etPhotoUrl.text.toString().trim()
                    }
                    if (mIsEditMode)
                        mEditStoreViewModel.updateStore(mStoreEntity)
                    else mEditStoreViewModel.saveStore(mStoreEntity)

                }

                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true

        for (textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
            if (!isValid) Snackbar.make(
                mBinding.root,
                R.string.edit_store_message_valid,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return isValid
    }

    private fun hideKeyBoard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        hideKeyBoard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mEditStoreViewModel.setShowFab(true)
        mEditStoreViewModel.setResult(Any())

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}