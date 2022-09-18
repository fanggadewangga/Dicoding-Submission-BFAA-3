package com.fangga.bfaa.bfaa_2.presentation.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    suspend fun getTheme() = repository.getTheme().asLiveData(Dispatchers.IO)
}