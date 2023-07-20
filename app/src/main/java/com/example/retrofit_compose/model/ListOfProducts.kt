package com.example.retrofit_compose.model

import com.google.gson.annotations.SerializedName

data class ListOfProducts(
    @SerializedName("products")
    val allProduct : List<ProductModel>,
    val total: Int
)
