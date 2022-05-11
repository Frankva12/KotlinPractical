package com.franciscostanleyvasconceloszelaya.stores.common.utils

import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)

    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)
}