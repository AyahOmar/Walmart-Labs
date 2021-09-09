package com.aya.walmarttask.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val productId: String,
    @ColumnInfo(name = "inStock") val inStock: Boolean?,
    @ColumnInfo(name = "longDescription") val longDescription: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "productImage") val productImage: String?,
    @ColumnInfo(name = "productName") val productName: String?,
    @ColumnInfo(name = "reviewCount") val reviewCount: Int?,
    @ColumnInfo(name = "reviewRating") val reviewRating: Float?,
    @ColumnInfo(name = "shortDescription") val shortDescription: String?
)