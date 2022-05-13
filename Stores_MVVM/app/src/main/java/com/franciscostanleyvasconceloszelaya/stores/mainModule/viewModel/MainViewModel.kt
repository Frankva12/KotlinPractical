package com.franciscostanleyvasconceloszelaya.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.mainModule.model.MainInteract

class MainViewModel : ViewModel() {
    private val interact: MainInteract = MainInteract()

    private val stores: MutableLiveData<List<StoreEntity>> by lazy {
        MutableLiveData<List<StoreEntity>>().also {
            loadStores()
        }
    }


    fun getStores(): LiveData<List<StoreEntity>> {
        return stores
    }

    private fun loadStores() {
//        interact.getStoresCallback(object : MainInteractor.StoresCallBack {
//            override fun getStoresCallback(callback: MutableList<StoreEntity>) {
//                stores.value = callback
//            }
//        })
        interact.getStores {
            stores.value = it
        }
    }
}