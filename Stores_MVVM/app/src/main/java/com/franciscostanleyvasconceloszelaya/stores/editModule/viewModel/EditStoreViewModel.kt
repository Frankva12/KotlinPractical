package com.franciscostanleyvasconceloszelaya.stores.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.editModule.model.EditStoreInteract

class EditStoreViewModel : ViewModel() {
    private val storeSelected = MutableLiveData<StoreEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()

    private val interact: EditStoreInteract = EditStoreInteract()

    fun setStoreSelected(storeEntity: StoreEntity) {
        storeSelected.value = storeEntity
    }

    fun getStoreSelected(): LiveData<StoreEntity> {
        return storeSelected
    }

    fun setShowFab(isVisible: Boolean) {
        showFab.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean> {
        return showFab
    }

    fun setResult(value: Any) {
        result.value = value
    }

    fun getResult(): LiveData<Any> {
        return result
    }

    fun saveStore(storeEntity: StoreEntity) {
        interact.saveStore(storeEntity) { newId ->
            result.value = newId
        }
    }


    fun updateStore(storeEntity: StoreEntity) {
        interact.updateStore(storeEntity) { storeUpdated ->
            result.value = storeUpdated
        }
    }
}