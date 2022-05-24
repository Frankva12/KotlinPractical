package com.franciscostanleyvasconceloszelaya.stores.mainModule.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import kotlinx.coroutines.delay
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteract {

    /*fun getStoresFireBase(callback: (MutableList<StoreEntity>) -> Unit) {
        val url = Constants.STORES_URL + Constants.GET_ALL_PATH

        var storeList = mutableListOf<StoreEntity>()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.i("Response", response.toString())

            val status = response.optInt(Constants.STATUS_PROPERTY, Constants.ERROR)
            Log.i("status", status.toString())
            if (status == Constants.SUCCESS) {

                val jsonList = response.optJSONArray(Constants.STORES_PROPERTY)?.toString()
                if (jsonList != null) {
                    val mutableListType = object : TypeToken<MutableList<StoreEntity>>() {}.type
                    storeList = Gson().fromJson(jsonList, mutableListType)

                    callback(storeList)
                    return@JsonObjectRequest
                }
            }
            callback(storeList)
        }, {
            it.printStackTrace()
            callback(storeList)
        })

        StoreApplication.storesAPI.addToRequestQueue(jsonObjectRequest)
    }*/

    /*fun getStores(callback: (MutableList<StoreEntity>) -> Unit) {
        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                val json = Gson().toJson(storeList)
                Log.i("GSON", json)
                callback(storeList)
            }
        }
    }
*/

    val stores: LiveData<MutableList<StoreEntity>> = liveData {
        delay(1_000)//temp for testing
        val storesLiveData = StoreApplication.database.storeDao().getAllStores()

        emitSource(storesLiveData.map { stores ->
            stores.sortedBy { it.name }.toMutableList()
        })
    }

    fun deleteStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit) {
        doAsync {
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }

    suspend fun updateStore(storeEntity: StoreEntity) {
        delay(300)
        StoreApplication.database.storeDao().updateStore(storeEntity)
    }
}