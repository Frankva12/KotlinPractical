package com.franciscostanleyvasconceloszelaya.coupons.mainModule.model

import android.database.sqlite.SQLiteConstraintException
import com.franciscostanleyvasconceloszelaya.coupons.CouponsApplication
import com.franciscostanleyvasconceloszelaya.coupons.common.dataAccess.CouponDAO
import com.franciscostanleyvasconceloszelaya.coupons.common.entities.CouponEntity
import com.franciscostanleyvasconceloszelaya.coupons.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class RoomDatabase {
    private val dao: CouponDAO by lazy { CouponsApplication.database.couponDao() }

    suspend fun consultCouponByCode(code: String) = dao.consultCouponByCode(code)

    suspend fun saveCoupon(couponEntity: CouponEntity) = withContext(Dispatchers.IO) {
        try {
            dao.addCoupon(couponEntity)
        } catch (e: Exception) {
            (e as? SQLiteConstraintException)?.let { throw Exception(Constants.ERROR_EXIST) }
            throw Exception(e.message ?: Constants.ERROR_UNKNOWN)
        }
    }
}