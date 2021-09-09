package com.aya.walmarttask.data.room

import com.aya.walmarttask.data.model.Product

interface DatabaseHelper {

    suspend fun getProducts(): List<Product>

    suspend fun insertAll(products: List<Product>)

    suspend fun deleteALlProducts()
}