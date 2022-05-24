package com.franciscostanleyvasconceloszelaya.stores.editModule.model

import androidx.lifecycle.LiveData
import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreInteract {

    fun getStoreById(id: Long): LiveData<StoreEntity> {
        return StoreApplication.database.storeDao().getStoreById(id)
    }

    fun saveStore(storeEntity: StoreEntity, callback: (Long) -> Unit) {
        doAsync {
            val newId = StoreApplication.database.storeDao().addStore(storeEntity)
            StoreApplication.database.storeDao().addStore(storeEntity)
            uiThread {
                callback(newId)
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit) {
        doAsync {
            StoreApplication.database.storeDao().updateStore(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }
}