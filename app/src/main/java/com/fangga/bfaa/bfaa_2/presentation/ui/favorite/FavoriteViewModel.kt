package com.fangga.bfaa.bfaa_2.presentation.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.Repository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getFavorites() = repository.getFavorites().asLiveData()
}