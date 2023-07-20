package com.example.retrofit_compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_compose.model.ListOfProducts
import com.example.retrofit_compose.repository.MainRepository
import com.example.retrofit_compose.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val repository: MainRepository)
    : ViewModel() {

        val response: MutableState<DataState<ListOfProducts>> = mutableStateOf(DataState.Empty)

    init {
        getProduct()
    }

    private fun getProduct() = viewModelScope.launch {
        repository.getListOfProduct()
            .onStart {
                response.value = DataState.Loading
            }.catch {
                response.value = DataState.Error(it.toString())
            }.collect {
                when(it){
                    is DataState.Success -> {
                        response.value = DataState.Success(it.data)
                    }
                    is DataState.Error -> {
                        response.value = DataState.Error(exception = it.exception)
                    }
                    is DataState.Loading -> {
                        response.value = DataState.Loading
                    }
                    is DataState.Empty -> {
                        response.value = DataState.Empty
                    }
                }
            }
    }


}