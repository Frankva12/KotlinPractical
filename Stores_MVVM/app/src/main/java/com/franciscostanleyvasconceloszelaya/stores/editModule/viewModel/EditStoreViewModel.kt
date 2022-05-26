package com.franciscostanleyvasconceloszelaya.stores.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.StoresException
import com.franciscostanleyvasconceloszelaya.stores.common.utils.TypeError
import com.franciscostanleyvasconceloszelaya.stores.editModule.model.EditStoreInteract
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditStoreViewModel : ViewModel() {
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()
    private var storeId: Long = 0

    private val typeError: MutableLiveData<TypeError> = MutableLiveData()

    private val interact: EditStoreInteract = EditStoreInteract()

    fun setStoreSelected(storeEntity: StoreEntity) {
        storeId = storeEntity.id
    }

    fun getTypeError(): MutableLiveData<TypeError> = typeError

    fun getStoreSelected(): LiveData<StoreEntity> {
        return interact.getStoreById(storeId)
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
        executeAction(storeEntity) { interact.saveStore(storeEntity) }
    }


    fun updateStore(storeEntity: StoreEntity) {
        executeAction(storeEntity) { interact.updateStore(storeEntity) }
    }

    private fun executeAction(storeEntity: StoreEntity, block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
                result.value = storeEntity
            } catch (e: StoresException) {
                typeError.value = e.typeError
            }
        }
    }
}