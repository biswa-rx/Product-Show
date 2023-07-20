package com.example.retrofit_compose.Network

import com.example.retrofit_compose.model.ListOfProducts
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ListOfProducts
}