package com.franciscostanleyvasconceloszelaya.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.Constants
import com.franciscostanleyvasconceloszelaya.stores.common.utils.StoresException
import com.franciscostanleyvasconceloszelaya.stores.common.utils.TypeError
import com.franciscostanleyvasconceloszelaya.stores.mainModule.model.MainInteract
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val interact: MainInteract = MainInteract()
    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    private val typeError: MutableLiveData<TypeError> = MutableLiveData()
    private val stores = interact.stores


    fun getStores(): LiveData<MutableList<StoreEntity>> {
        return stores
    }

    fun getTypeError(): MutableLiveData<TypeError> = typeError

    fun isShowProgress(): LiveData<Boolean> {
        return showProgress
    }

    fun deleteStore(storeEntity: StoreEntity) {
        executeAction { interact.deleteStore(storeEntity) }
    }

    fun updateStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        executeAction { interact.updateStore(storeEntity) }
    }


    private fun executeAction(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            showProgress.value = Constants.SHOW
            try {
                block()
            } catch (e: StoresException) {
                typeError.value = e.typeError
            } finally {
                showProgress.value = Constants.HIDE
            }
        }
    }

}