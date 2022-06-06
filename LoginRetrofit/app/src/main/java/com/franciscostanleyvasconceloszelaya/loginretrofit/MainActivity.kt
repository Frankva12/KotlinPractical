package com.franciscostanleyvasconceloszelaya.loginretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.franciscostanleyvasconceloszelaya.loginretrofit.databinding.ActivityMainBinding
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.LoginService
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.swType.setOnCheckedChangeListener { button, checked ->
            button.text =
                if (checked) getString(R.string.main_type_login)
                else getString(R.string.main_type_register)

            mBinding.btnLogin.text = button.text
        }
        mBinding.btnLogin.setOnClickListener {
            if (mBinding.swType.isChecked) login() else register()
        }
        mBinding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }


    private fun login() {
        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(LoginService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.loginUser(UserInfo(email, password))
                updateUI(
                    " ${Constants.TOKEN_PROPERTY}: ${result.token}"
                )
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }
            }
        }

        /*service.login(UserInfo(email, password)).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            val result = response.body()
                            updateUI("${Constants.TOKEN_PROPERTY}: ${result?.token}")
                        }
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Retrofit", "Problems with the server.")
                }
            }
        )*/
    }

    private fun register() {
        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(LoginService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.registerUser(UserInfo(email, password))
                updateUI(
                    "${Constants.ID_PROPERTY}: ${result.id}," +
                            " ${Constants.TOKEN_PROPERTY}: ${result.token}"
                )

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }
            }
        }

    }

    private suspend fun updateUI(result: String) = withContext(Dispatchers.Main) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}