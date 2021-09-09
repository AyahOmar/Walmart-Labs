package com.aya.walmarttask.data.repository

import com.aya.walmarttask.data.api.ApiHelper
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.data.room.DatabaseHelper

class MainRepository (private val apiHelper: ApiHelper,private val dbHelper: DatabaseHelper) {

    suspend fun getProduct(pageNum: Int , pageSize: Int) = apiHelper.getProduct(pageNum, pageSize)

    suspend fun getProductDB(): List<Product> = dbHelper.getProducts()
    suspend fun insertProductDB(products: List<Product>) = dbHelper.insertAll(products)

    suspend fun deleteHomProductsDB() = dbHelper.deleteALlProducts()

}