package com.fangga.bfaa.bfaa_2.presentation.ui.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Repository

class FollowingViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getUserFollowing(username: String) = repository.getUserFollowing(username).asLiveData()
}