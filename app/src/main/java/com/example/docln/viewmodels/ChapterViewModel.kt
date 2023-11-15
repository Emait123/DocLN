package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
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

    //Reader Settings
    var backgroundColor by mutableStateOf(Color.White)
    var fontColor by mutableStateOf(Color.Black)
    var fontSize by mutableStateOf(18)
    var fontStyle by mutableStateOf(FontFamily.Default)
    var textAlign by mutableStateOf(TextAlign.Justify)

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

    fun changeBGColor(newBackgroundColor: Color) {
        backgroundColor = newBackgroundColor
        fontColor = if (backgroundColor == Color.Black) {
            Color.White
        } else {
            Color.Black
        }
    }

    fun incFontSize() {
        fontSize++
    }

    fun decFontSize() {
        fontSize--
    }

    fun changeFontStyle(newFontStyle: SystemFontFamily) {
        fontStyle = newFontStyle
    }

    fun changeTextAlign(newTextAlign: TextAlign) {
        textAlign = newTextAlign
    }
}