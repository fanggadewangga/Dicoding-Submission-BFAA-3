package com.fangga.bfaa.bfaa_2.presentation.ui.follower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fangga.bfaa.bfaa_2.data.Repository

class FollowerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getUserFollowers(username: String) = repository.getUserFollowers(username)
}