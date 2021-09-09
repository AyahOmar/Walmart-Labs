package com.aya.walmarttask.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aya.walmarttask.data.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    suspend fun getAll(): List<Product>

    @Insert
    suspend fun insertAll(users: List<Product>)

    @Query("DELETE FROM Product")
    suspend fun deleteAllHomeProducts()

}