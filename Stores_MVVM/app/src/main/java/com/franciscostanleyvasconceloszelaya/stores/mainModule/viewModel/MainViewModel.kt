package com.franciscostanleyvasconceloszelaya.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.Constants
import com.franciscostanleyvasconceloszelaya.stores.mainModule.model.MainInteract
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private var storeList: MutableList<StoreEntity> = mutableListOf()
    private val interact: MainInteract = MainInteract()

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()


    /*private val stores: MutableLiveData<MutableList<StoreEntity>> by lazy {
        MutableLiveData<MutableList<StoreEntity>>().also {
            loadStores()
        }
    }*/

    private val stores = interact.stores


    fun getStores(): LiveData<MutableList<StoreEntity>> {
        return stores
    }

    fun isShowProgress(): LiveData<Boolean> {
        return showProgress
    }

    /*private fun loadStores() {
        showProgress.value = Constants.SHOW
        interact.getStores {
            showProgress.value = Constants.HIDE
            stores.value = it
            storeList = it
        }
    }*/

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
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                showProgress.value = Constants.HIDE
            }
        }
    }
}