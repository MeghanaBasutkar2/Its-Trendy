package com.example.itstrending.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//this is needed to create the ViewModel class which takes a constructor argument
class ViewModelFactory constructor(private val app: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingViewModel::class.java)) {
           return TrendingViewModel(app) as T
        }
        throw IllegalArgumentException("ViewModel not Found exception")
    }
}

