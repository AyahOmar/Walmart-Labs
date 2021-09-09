package com.aya.walmarttask.data.model

data class ProductList(
    val pageNumber: Int,
    val pageSize: Int,
    val products: List<Product>,
    val statusCode: Int,
    val totalProducts: Int
)