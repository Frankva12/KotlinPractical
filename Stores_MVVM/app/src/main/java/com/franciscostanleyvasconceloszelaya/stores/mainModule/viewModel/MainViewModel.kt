package com.franciscostanleyvasconceloszelaya.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.Constants
import com.franciscostanleyvasconceloszelaya.stores.mainModule.model.MainInteract

class MainViewModel : ViewModel() {
    private var storeList: MutableList<StoreEntity> = mutableListOf()
    private val interact: MainInteract = MainInteract()

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()


    private val stores: MutableLiveData<MutableList<StoreEntity>> by lazy {
        MutableLiveData<MutableList<StoreEntity>>().also {
            loadStores()
        }
    }


    fun getStores(): LiveData<MutableList<StoreEntity>> {
        return stores
    }

    fun isShowProgress(): LiveData<Boolean> {
        return showProgress
    }

    private fun loadStores() {
        showProgress.value = Constants.SHOW
        interact.getStores {
            showProgress.value = Constants.HIDE
            stores.value = it
            storeList = it
        }
    }

    fun deleteStore(storeEntity: StoreEntity) {
        interact.deleteStore(storeEntity) {
            val index = storeList.indexOf(it)
            if (index != -1) {
                storeList.removeAt(index)
                stores.value = storeList
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        interact.updateStore(storeEntity) {
            val index = storeList.indexOf(storeEntity)
            if (index != -1) {
                storeList[index] = storeEntity
                stores.value = storeList
            }
        }
    }
}