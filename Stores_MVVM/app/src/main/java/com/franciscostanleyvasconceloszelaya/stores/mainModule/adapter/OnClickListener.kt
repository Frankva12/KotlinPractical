package com.franciscostanleyvasconceloszelaya.stores.mainModule.adapter

import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity

interface OnClickListener {
    fun onClick(storeEntity: StoreEntity)
    fun onFavoriteStore(storeEntity: StoreEntity)
    fun onDeleteStore(storeEntity: StoreEntity)
}