package com.franciscostanleyvasconceloszelaya.coupons

import android.app.Application
import androidx.room.Room
import com.franciscostanleyvasconceloszelaya.coupons.common.dataAccess.CouponDatabase

class CouponsApplication : Application() {
    companion object {
        lateinit var database: CouponDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, CouponDatabase::class.java, "CouponDatabase").build()
    }
}