package com.franciscostanleyvasconceloszelaya.coupons.mainModule.model

import com.franciscostanleyvasconceloszelaya.coupons.common.entities.CouponEntity
import com.franciscostanleyvasconceloszelaya.coupons.common.utils.Constants
import com.franciscostanleyvasconceloszelaya.coupons.common.utils.validateTextCode

class MainRepository {
    private val roomDatabase = RoomDatabase()

    suspend fun consultCouponByCode(code: String) = roomDatabase.consultCouponByCode(code)

    suspend fun saveCoupon(couponEntity: CouponEntity) {
        if (validateTextCode(couponEntity.code)) {
            roomDatabase.saveCoupon(couponEntity)
        } else {
            throw Exception(Constants.ERROR_LENGTH)
        }
    }
}