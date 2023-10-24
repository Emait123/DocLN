package com.example.docln.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun searchNovel(name: String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelList = apiService.searchNovel(name)
                novelListResponse = novelList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("e", errorMessage)
            }
        }
    }
}