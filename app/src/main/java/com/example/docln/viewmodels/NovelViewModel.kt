package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.NovelDetail
import com.example.docln.RetrofitAPI
import kotlinx.coroutines.launch

class NovelViewModel : ViewModel() {
    var novelDetailResponse: List<NovelDetail> by mutableStateOf(listOf())
    private set
    var errorMessage: String by mutableStateOf("")

    fun getNovelDetail(id: Int) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(id)
                novelDetailResponse = listOf(novelDetail)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}