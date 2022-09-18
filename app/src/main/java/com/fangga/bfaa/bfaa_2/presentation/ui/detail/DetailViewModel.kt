package com.fangga.bfaa.bfaa_2.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fangga.bfaa.bfaa_2.data.Repository
import com.fangga.bfaa.bfaa_2.data.model.User
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getUserDetail(username: String?) = repository.getUserDetail(username).asLiveData()

    fun insertFavoritedUser(user: User) {
        viewModelScope.launch {
            repository.insertFavoritedUser(user)
        }
    }

    fun deleteFavoritedUser(user: User) {
        viewModelScope.launch {
            repository.deleteFavoritedUser(user)
        }
    }
}