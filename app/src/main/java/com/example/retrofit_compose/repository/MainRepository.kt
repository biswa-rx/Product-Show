package com.example.retrofit_compose.repository

import android.util.Log
import com.example.retrofit_compose.Network.ApiService
import com.example.retrofit_compose.model.ListOfProducts
import com.example.retrofit_compose.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getListOfProduct(): Flow<DataState<ListOfProducts>> = flow{
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1000)
        try {
            val allProductList = apiService.getProducts()
            Log.d("BISWA_RX", "getListOfProduct: ${allProductList.toString()}")
            emit(DataState.Success(allProductList))
        }catch (e : Exception){
            emit(DataState.Error(e.toString()))
        }

    }
}