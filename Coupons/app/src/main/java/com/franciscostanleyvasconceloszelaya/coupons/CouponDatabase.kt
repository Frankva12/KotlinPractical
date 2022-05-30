package com.franciscostanleyvasconceloszelaya.coupons

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CouponEntity::class], version = 1)
abstract class CouponDatabase : RoomDatabase() {
    abstract fun couponDao(): CouponDAO
}