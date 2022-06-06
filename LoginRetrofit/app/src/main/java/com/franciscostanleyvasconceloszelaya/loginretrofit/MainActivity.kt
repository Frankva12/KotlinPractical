package com.franciscostanleyvasconceloszelaya.loginretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.franciscostanleyvasconceloszelaya.loginretrofit.databinding.ActivityMainBinding
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.LoginResponse
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.LoginService
import com.franciscostanleyvasconceloszelaya.loginretrofit.retrofit.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
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
            login()
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
        service.login(UserInfo(email, password)).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    val result = response.body()
                    updateUI("${Constants.TOKEN_PROPERTY}: ${result?.token}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Retrofit", "Problems with the server.")
                }
            }
        )

        /*val typeMethod =
            if (mBinding.swType.isChecked) Constants.LOGIN_PATH else Constants.REGISTER_PATH

        val url = Constants.BASE_URL + Constants.API_PATH + typeMethod


        val jsonParams = JSONObject()

        if (email.isNotEmpty()) {
            jsonParams.put(Constants.EMAIL_PARAM, email)
        }
        if (password.isNotEmpty()) {
            jsonParams.put(Constants.PASSWORD_PARAM, password)
        }

        val jsonObjectRequest =
            object : JsonObjectRequest(Method.POST, url, jsonParams, { response ->
                Log.i("response", response.toString())

                val id = response.optString(Constants.ID_PROPERTY, Constants.ERROR_VALUE)
                val token = response.optString(Constants.TOKEN_PROPERTY, Constants.ERROR_VALUE)

                val result =
                    if (id.equals(Constants.ERROR_VALUE)) "${Constants.TOKEN_PROPERTY}: $token"
                    else "${Constants.ID_PROPERTY}: $id, ${Constants.TOKEN_PROPERTY}: $token"

                updateUI(result)
            }, {
                it.printStackTrace()
                if (it.networkResponse.statusCode == 400) {
                    updateUI(getString(R.string.main_error_server))
                }
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["Content-Type"] = "application/json"
                    return params
                }
            }
        LoginApplication.reqResAPI.addToRequestQueue(jsonObjectRequest)*/
    }

    private fun updateUI(result: String) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}