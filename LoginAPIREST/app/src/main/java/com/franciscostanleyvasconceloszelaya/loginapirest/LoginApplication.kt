package com.franciscostanleyvasconceloszelaya.stores

import android.app.Application
import com.franciscostanleyvasconceloszelaya.stores.common.database.ReqResAPI

class LoginApplication : Application() {
    companion object {
        lateinit var reqResAPI: ReqResAPI
    }

    override fun onCreate() {
        super.onCreate()

        //Volley
        reqResAPI = ReqResAPI.getInstance(this)
    }
}