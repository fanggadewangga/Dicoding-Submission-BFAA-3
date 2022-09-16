package com.fangga.bfaa.bfaa_2.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.remote.ApiService
import com.fangga.bfaa.bfaa_2.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetailViewModel: ViewModel() {
    private val retrofit: ApiService = RetrofitInstance.create()

    fun getUserDetail(username: String?) = flow{
        try {
            val result = retrofit.getUserDetail(username.toString())
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO).asLiveData()
}