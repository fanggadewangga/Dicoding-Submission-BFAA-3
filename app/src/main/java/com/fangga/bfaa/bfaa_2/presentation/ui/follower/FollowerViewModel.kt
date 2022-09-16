package com.fangga.bfaa.bfaa_2.presentation.ui.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.remote.ApiService
import com.fangga.bfaa.bfaa_2.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FollowerViewModel : ViewModel() {
    private val retrofit: ApiService = RetrofitInstance.create()

    fun getUserFollowers(username: String) =
        flow {
            emit(Resource.Loading())
            try {
                val list = retrofit.getUserFollowers(username)
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