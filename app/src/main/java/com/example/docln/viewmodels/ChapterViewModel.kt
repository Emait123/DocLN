package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.ChapterContent
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class ChapterViewModel : ViewModel() {
    var chapContentResponse: List<ChapterContent> by mutableStateOf(listOf())
        private set
    var errorMessage: String by mutableStateOf("")

    fun ChapContent(id: Int) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val chapContent = apiService.getChapterContent(id)
                chapContentResponse = listOf(chapContent)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}