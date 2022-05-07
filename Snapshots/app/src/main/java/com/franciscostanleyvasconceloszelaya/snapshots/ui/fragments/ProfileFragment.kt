package com.franciscostanleyvasconceloszelaya.snapshots.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.franciscostanleyvasconceloszelaya.snapshots.R
import com.franciscostanleyvasconceloszelaya.snapshots.SnapshotsApplication
import com.franciscostanleyvasconceloszelaya.snapshots.databinding.FragmentProfileBinding
import com.franciscostanleyvasconceloszelaya.snapshots.utils.FragmentAux
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment(), FragmentAux {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh()
        setUpButtom()
    }

    private fun setUpButtom() {
        mBinding.btnLogout.setOnClickListener {
            context?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle(R.string.dialog_logout_title)
                    .setPositiveButton(R.string.dialog_logout_confirm) { _, _ ->
                        signOut()
                    }
                    .setNegativeButton(R.string.dialog_logout_cancel, null)
                    .show()
            }
        }
    }

    private fun signOut() {
        context?.let {
            AuthUI.getInstance().signOut(it)
                .addOnCompleteListener {
                    Toast.makeText(context, getString(R.string.log_out_message), Toast.LENGTH_SHORT)
                        .show()
                    mBinding.tvName.text = ""
                    mBinding.tvEmail.text = ""

                    (activity?.findViewById(R.id.bottomNav) as? BottomNavigationView)?.selectedItemId =
                        R.id.action_home
                }
        }
    }

    override fun refresh() {
        with(mBinding) {
            tvName.text = SnapshotsApplication.currentUser.displayName
            tvEmail.text = SnapshotsApplication.currentUser.email
        }
    }
}