package com.franciscostanleyvasconceloszelaya.stores.mainModule.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.StoresException
import com.franciscostanleyvasconceloszelaya.stores.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainInteract {
    val stores: LiveData<MutableList<StoreEntity>> = liveData {
        delay(1_000)//temp for testing
        val storesLiveData = StoreApplication.database.storeDao().getAllStores()

        emitSource(storesLiveData.map { stores ->
            stores.sortedBy { it.name }.toMutableList()
        })
    }

    suspend fun deleteStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO){
        delay(1_000)
        val result = StoreApplication.database.storeDao().deleteStore(storeEntity)
        if (result == 0) throw StoresException(typeError = TypeError.DELETE)
    }

    suspend fun updateStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO) {
        delay(300)
        val result = StoreApplication.database.storeDao().updateStore(storeEntity)
        if (result == 0) throw StoresException(typeError = TypeError.UPDATE)
    }
}