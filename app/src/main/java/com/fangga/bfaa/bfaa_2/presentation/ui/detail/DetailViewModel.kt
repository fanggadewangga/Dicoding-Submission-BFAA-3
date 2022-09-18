package com.fangga.bfaa.bfaa_2.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fangga.bfaa.bfaa_2.data.Repository

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getUserDetail(username: String?) = repository.getUserDetail(username)
}