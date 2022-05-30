package com.franciscostanleyvasconceloszelaya.coupons.common.dataAccess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.franciscostanleyvasconceloszelaya.coupons.common.entities.CouponEntity

@Dao
interface CouponDAO {
    @Query("Select * FROM CouponEntity WHERE code = :code")
    suspend fun consultCouponByCode(code: String): CouponEntity?

    @Insert
    suspend fun addCoupon(couponEntity: CouponEntity): Long
}