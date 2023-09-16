package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getNovelList() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelList = apiService.getNovelList()
//                println(novelList)
                novelListResponse = novelList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}