package com.fangga.bfaa.bfaa_2.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.remote.ApiService
import com.fangga.bfaa.bfaa_2.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainViewModel : ViewModel() {

    private val retrofit: ApiService = RetrofitInstance.create()

    fun searchUsers(query: String) =
        flow {
            emit(Resource.Loading())
            try {
                val list = retrofit.searchUser(query).items
                if (list.isEmpty())
                    emit(Resource.Error(null))
                else {
                    emit(Resource.Success(list))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO).asLiveData()
}