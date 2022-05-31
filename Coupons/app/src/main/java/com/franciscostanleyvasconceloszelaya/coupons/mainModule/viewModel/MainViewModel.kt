package com.franciscostanleyvasconceloszelaya.coupons.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscostanleyvasconceloszelaya.coupons.R
import com.franciscostanleyvasconceloszelaya.coupons.common.entities.CouponEntity
import com.franciscostanleyvasconceloszelaya.coupons.common.utils.getMsgErrorByCode
import com.franciscostanleyvasconceloszelaya.coupons.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    val coupon = MutableLiveData(CouponEntity())

    private val hideKeyBoard = MutableLiveData<Boolean>()
    fun isHideKeyBoard() = hideKeyBoard

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackBarMsg() = snackbarMsg

    fun consultCouponByCode() {
        coupon.value?.code?.let { code ->
            viewModelScope.launch {
                hideKeyBoard.value = true
                coupon.value = repository.consultCouponByCode(code) ?: CouponEntity(
                    code = code,
                    isActive = false
                )
            }
        }
    }

    fun saveCoupon() {
        coupon.value?.let { couponEntity ->
            viewModelScope.launch {
                hideKeyBoard.value = true
                try {
                    couponEntity.isActive = true
                    repository.saveCoupon(couponEntity)
                    consultCouponByCode()
                    snackbarMsg.value = R.string.main_save_success
                } catch (e: Exception) {
                    snackbarMsg.value = getMsgErrorByCode(e.message)
                }
            }
        }
    }
}