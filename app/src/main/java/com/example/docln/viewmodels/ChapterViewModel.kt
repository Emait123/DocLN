package com.example.docln.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.ChapterContent
import com.example.docln.plugins.AppDataStore
import com.example.docln.plugins.ReaderSetting
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class ChapterViewModel : ViewModel() {
    var chapContentResponse: List<ChapterContent> by mutableStateOf(listOf())
        private set
    var errorMessage: String by mutableStateOf("")
    var novel_ID: Int by mutableIntStateOf(0)
    private lateinit var dataStore: AppDataStore
    lateinit var readerSetting: ReaderSetting

    //Reader Settings
//    var backgroundColor by mutableStateOf(Color.White)
//    var fontColor by mutableStateOf(Color.Black)
//    var fontSize by mutableStateOf(18)
//    var fontStyle by mutableStateOf(FontFamily.Default)
//    var textAlign by mutableStateOf(TextAlign.Justify)

    var backgroundColor by mutableIntStateOf(0)
    var fontColor by mutableIntStateOf(0)
    var fontSize by mutableIntStateOf(18)
    var fontStyle by mutableIntStateOf(0)
    var textAlign by mutableIntStateOf(0)

    fun chapContent(novelID: Int, chapID: Int) {
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

    fun changeBGColor(newBackgroundColor: Color, index: Int) {
        backgroundColor = index
        if (backgroundColor == 2) {
            fontColor = 1
        } else {
            fontColor = 0
        }
        saveReaderSettings()
//        fontColor = if (backgroundColor == Color.Black) {
//            Color.White
//        } else {
//            Color.Black
//        }
    }

    fun incFontSize() {
        fontSize++
        saveReaderSettings()
    }

    fun decFontSize() {
        if (fontSize > 0) {
            fontSize--
            saveReaderSettings()
        }
    }

    fun changeFontStyle(newFontStyle: SystemFontFamily, index: Int) {
        fontStyle = index
        saveReaderSettings()
    }

    fun changeTextAlign(newTextAlign: TextAlign, index: Int) {
        textAlign = index
        saveReaderSettings()
    }

    fun createDataStore(context: Context) {
        dataStore = AppDataStore(context)
        viewModelScope.launch {
            readerSetting = dataStore.getReaderSettings()
            println(readerSetting.toString())
        }
            backgroundColor = readerSetting.backgroundColor
            fontColor = readerSetting.fontColor
            fontSize = readerSetting.fontSize
            fontStyle = readerSetting.fontStyle
            textAlign = readerSetting.textAlign
    }

    fun saveReaderSettings() {
        viewModelScope.launch {
            dataStore.updateReaderSettings(backgroundColor, fontColor, fontSize, fontStyle, textAlign)
        }
    }
}