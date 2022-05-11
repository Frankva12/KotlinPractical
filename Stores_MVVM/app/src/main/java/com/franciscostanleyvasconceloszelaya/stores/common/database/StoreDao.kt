package com.franciscostanleyvasconceloszelaya.stores.common.database

import androidx.room.*
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * from StoreEntity")
    fun getAllStores(): MutableList<StoreEntity>

    @Query("SELECT * from StoreEntity where id = :id")
    fun getStoreById(id: Long): StoreEntity

    @Insert
    fun addStore(storeEntity: StoreEntity): Long

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)
}