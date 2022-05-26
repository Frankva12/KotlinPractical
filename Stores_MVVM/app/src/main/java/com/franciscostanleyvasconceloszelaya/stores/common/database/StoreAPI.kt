package com.franciscostanleyvasconceloszelaya.stores.common.database

import android.content.Context

class StoreAPI {
    companion object {
        @Volatile
        private var INSTANCE: StoreAPI? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: StoreAPI().also { INSTANCE = it }
        }
    }

}