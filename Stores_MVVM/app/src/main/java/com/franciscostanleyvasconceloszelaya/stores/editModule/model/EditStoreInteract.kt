package com.franciscostanleyvasconceloszelaya.stores.editModule.model

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.StoresException
import com.franciscostanleyvasconceloszelaya.stores.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditStoreInteract {

    fun getStoreById(id: Long): LiveData<StoreEntity> {
        return StoreApplication.database.storeDao().getStoreById(id)
    }

    suspend fun saveStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO) {
        try {
            val result = StoreApplication.database.storeDao().addStore(storeEntity)
            if (result == 0L) throw StoresException(TypeError.INSERT)
        } catch (e: SQLiteConstraintException) {
            throw StoresException(TypeError.INSERT_NAME)
        }
    }

    suspend fun updateStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO) {
        try {
            val result = StoreApplication.database.storeDao().updateStore(storeEntity)
            if (result == 0) throw StoresException(TypeError.UPDATE)
        } catch (e: SQLiteConstraintException) {
            throw StoresException(TypeError.UPDATE_NAME)
        }
    }
}