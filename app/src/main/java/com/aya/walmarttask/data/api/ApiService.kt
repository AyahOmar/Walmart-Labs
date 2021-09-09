package com.aya.walmarttask.data.api

import com.aya.walmarttask.data.model.ProductList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/walmartproducts/{pageNumber}/{pageSize}")
    suspend fun getProduct(@Path("pageNumber") pageNumber: Int,
                           @Path("pageSize") pageSize: Int): ProductList

}