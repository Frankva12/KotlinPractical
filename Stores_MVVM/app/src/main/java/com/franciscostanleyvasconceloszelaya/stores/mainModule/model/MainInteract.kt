package com.franciscostanleyvasconceloszelaya.stores.mainModule.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import com.franciscostanleyvasconceloszelaya.stores.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteract {

    fun getStores(callback: (MutableList<StoreEntity>) -> Unit) {
        val url = Constants.STORES_URL + Constants.GET_ALL_PATH

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.i("Response", response.toString())

            val status = response.optInt(Constants.STATUS_PROPERTY, Constants.ERROR)
            Log.i("status", status.toString())

            if (status == Constants.SUCCESS) {

                val jsonList = response.optJSONArray(Constants.STORES_PROPERTY)?.toString()
                if (jsonList != null) {
                    val mutableListType = object : TypeToken<MutableList<StoreEntity>>() {}.type
                    val storeList =
                        Gson().fromJson<MutableList<StoreEntity>>(jsonList, mutableListType)

                    callback(storeList)
                }
            }
        }, {
            it.printStackTrace()
        })

        StoreApplication.storesAPI.addToRequestQueue(jsonObjectRequest)
    }

    fun getStoresRoom(callback: (MutableList<StoreEntity>) -> Unit) {
        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                val json = Gson().toJson(storeList)
                Log.i("GSON", json)
                callback(storeList)
            }
        }
    }

    fun deleteStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit) {
        doAsync {
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            uiThread {
                callback(storeEntity)
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