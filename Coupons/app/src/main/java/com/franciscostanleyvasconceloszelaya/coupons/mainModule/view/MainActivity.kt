package com.franciscostanleyvasconceloszelaya.coupons.mainModule.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.franciscostanleyvasconceloszelaya.coupons.BR
import com.franciscostanleyvasconceloszelaya.coupons.R
import com.franciscostanleyvasconceloszelaya.coupons.common.utils.hideKeyBoard
import com.franciscostanleyvasconceloszelaya.coupons.databinding.ActivityMainBinding
import com.franciscostanleyvasconceloszelaya.coupons.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
    }


    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let {
            it.coupon.observe(this@MainActivity) { coupon ->
                binding.isActive = coupon != null && coupon.isActive
            }
            it.getSnackBarMsg().observe(this@MainActivity) { msg ->
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
            it.isHideKeyBoard().observe(this@MainActivity) { isHide ->
                if (isHide) hideKeyBoard(this@MainActivity, binding.root)

            }
        }
    }
}