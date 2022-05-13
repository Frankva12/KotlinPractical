package com.franciscostanleyvasconceloszelaya.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.mainModule.model.MainInteractor

class MainViewModel : ViewModel() {
    private var stores: MutableLiveData<List<StoreEntity>> = MutableLiveData()
    private val interactor: MainInteractor = MainInteractor()

    init {
        loadStores()
    }

    fun getStores(): LiveData<List<StoreEntity>> {
        return stores
    }

    private fun loadStores(){
        interactor.getStoresCallback(object : MainInteractor.StoresCallBack{
            override fun getStoresCallback(callback: MutableList<StoreEntity>) {
                stores.value = callback
            }
        })
    }
}