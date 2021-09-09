package com.aya.walmarttask.data.room

import com.aya.walmarttask.data.model.Product

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getProducts(): List<Product> = appDatabase.productDao().getAll()

    override suspend fun insertAll(products: List<Product>) = appDatabase.productDao().insertAll(products)


    override suspend fun deleteALlProducts() {
        appDatabase.productDao().deleteAllHomeProducts()
    }

}