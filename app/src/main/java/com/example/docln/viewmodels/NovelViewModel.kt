package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.NovelDetail
import com.example.docln.ReviewContent
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class NovelViewModel : ViewModel() {
    var novelDetailResponse: List<NovelDetail> by mutableStateOf(listOf())
    private set
    var errorMessage: String by mutableStateOf("")
    private var novelID : Int = 1
    private var userID : Int = 1
    var reviewContent : List<ReviewContent> by mutableStateOf(listOf())

    fun getNovelDetail(id: Int) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(id)
                novelID = novelDetail.id_truyen
                novelDetailResponse = listOf(novelDetail)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun sendReview(rating : Float, content : String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                reviewContent = apiService.sendReview(novelID, userID, rating, content)
                println(reviewContent)
                //refresh du lieu
                getNovelDetail(novelID)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}