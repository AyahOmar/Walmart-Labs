package com.aya.walmarttask.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProduct(pageNum: Int , pageSize: Int) = apiService.getProduct(pageNum,pageSize)
}