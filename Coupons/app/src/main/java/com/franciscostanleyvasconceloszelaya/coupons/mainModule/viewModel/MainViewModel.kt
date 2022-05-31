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

    private val result = MutableLiveData<CouponEntity>()
    fun getResult() = result

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackBarMsg() = snackbarMsg

    fun consultCouponByCode(code: String) {
        viewModelScope.launch {
            result.value = repository.consultCouponByCode(code)
        }
    }

    fun saveCoupon(couponEntity: CouponEntity) {
        viewModelScope.launch {
            try {
                repository.saveCoupon(couponEntity)
                consultCouponByCode(couponEntity.code)
                snackbarMsg.value = R.string.main_save_success
            } catch (e: Exception) {
                snackbarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }
}