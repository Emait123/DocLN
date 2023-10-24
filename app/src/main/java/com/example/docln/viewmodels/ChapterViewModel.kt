package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.ChapterContent
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class ChapterViewModel : ViewModel() {
    var chapContentResponse: List<ChapterContent> by mutableStateOf(listOf())
        private set
    var errorMessage: String by mutableStateOf("")
    var novel_ID: Int by mutableIntStateOf(0)
    var vmColor by  mutableStateOf(Color.White)

    fun ChapContent(novelID: Int, chapID: Int) {
        viewModelScope.launch {
            novel_ID = novelID
            val apiService = RetrofitAPI.getInstance()
            try {
                val chapContent = apiService.getChapterContent(novelID, chapID)
                chapContentResponse = listOf(chapContent)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun changeSetting(color : Color) {
        vmColor = color
    }
}