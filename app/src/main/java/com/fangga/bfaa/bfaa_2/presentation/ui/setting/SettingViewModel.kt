package com.fangga.bfaa.bfaa_2.presentation.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fangga.bfaa.bfaa_2.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    suspend fun saveTheme(isLightModeActive: Boolean) = viewModelScope.launch {
        repository.saveTheme(isLightModeActive)
    }

    fun getTheme() = repository.getTheme().asLiveData(Dispatchers.IO)
}