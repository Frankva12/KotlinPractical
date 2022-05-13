package com.franciscostanleyvasconceloszelaya.stores.mainModule.model

import com.franciscostanleyvasconceloszelaya.stores.StoreApplication
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteract {

//    interface StoresCallBack{
//        fun getStoresCallback(callback: MutableList<StoreEntity>)
//    }
//
//    fun getStoresCallback(stores: StoresCallBack){
//        doAsync {
//            val storeList = StoreApplication.database.storeDao().getAllStores()
//            uiThread {
//                stores.getStoresCallback(storeList)
//            }
//        }
//    }

    fun getStores(callback: (MutableList<StoreEntity>) -> Unit){
        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                callback(storeList)
            }
        }
    }
}