package com.franciscostanleyvasconceloszelaya.loginretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.franciscostanleyvasconceloszelaya.loginretrofit.databinding.ActivityProfileBinding
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.UserService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(UserService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.getSingleUser()
                updateUI(result.data, result.support)
            } catch (e: Exception) {
                showMessage(getString(R.string.main_error_server))
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private suspend fun updateUI(user: User, support: Support) = withContext(Dispatchers.Main) {
        with(mBinding) {
            tvFullName.text = user.getFullName()

            Glide.with(this@ProfileActivity)
                .load(user.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(imgProfile)

            tvSupportMessage.text = support.text
            tvSupportUrl.text = support.url
        }
    }
}