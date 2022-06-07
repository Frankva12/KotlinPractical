package com.franciscostanleyvasconceloszelaya.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.franciscostanleyvasconceloszelaya.weather.BR
import com.franciscostanleyvasconceloszelaya.weather.R
import com.franciscostanleyvasconceloszelaya.weather.databinding.ActivityMainBinding
import com.franciscostanleyvasconceloszelaya.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpViewModel()
        setUpObservers()
    }

    private fun setUpViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setUpObservers() {
        binding.viewModel?.let {
            it.getSnackbarMsg().observe(this) { resMsg ->
                Snackbar.make(binding.root, resMsg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            binding.viewModel?.getWeatherAndForecast(
                13.7226999,
                -89.210953,
                "73686b2d4952bcbe03a7a3308b84ecd5",
                "metric",
                "en"
            )
        }
    }
}