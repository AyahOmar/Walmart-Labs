package com.aya.walmarttask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aya.walmarttask.data.api.ApiHelper
import com.aya.walmarttask.data.repository.MainRepository
import com.aya.walmarttask.data.room.DatabaseHelper
import com.aya.walmarttask.ui.main.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper,dbHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

