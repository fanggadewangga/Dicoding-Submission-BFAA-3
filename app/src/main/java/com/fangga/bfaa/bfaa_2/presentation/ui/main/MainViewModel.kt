package com.fangga.bfaa.bfaa_2.presentation.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fangga.bfaa.bfaa_2.data.Repository

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val repository = Repository(application)

    fun searchUsers(query: String) = repository.searchUsers(query)
}