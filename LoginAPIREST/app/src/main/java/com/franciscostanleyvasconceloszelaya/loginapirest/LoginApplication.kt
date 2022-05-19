package com.franciscostanleyvasconceloszelaya.stores

import android.app.Application
import com.franciscostanleyvasconceloszelaya.stores.common.database.ReqResAPI

class LoginApplication : Application() {
    companion object {
        lateinit var storesAPI: ReqResAPI
    }

    override fun onCreate() {
        super.onCreate()

        //Volley
        storesAPI = ReqResAPI.getInstance(this)
    }
}