package com.franciscostanleyvasconceloszelaya.stores.common.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.franciscostanleyvasconceloszelaya.stores.common.entities.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * from StoreEntity")
    fun getAllStores(): LiveData<MutableList<StoreEntity>>

    @Query("SELECT * from StoreEntity where id = :id")
    fun getStoreById(id: Long): LiveData<StoreEntity>

    @Insert
    fun addStore(storeEntity: StoreEntity): Long

    @Update
    suspend fun updateStore(storeEntity: StoreEntity)

    @Delete
    suspend fun deleteStore(storeEntity: StoreEntity)
}