package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.NovelDetail
import com.example.docln.ReviewContent
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NovelViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var novelDetailResponse: List<NovelDetail> by mutableStateOf(listOf())
    private set
    var errorMessage: String by mutableStateOf("")
    private var novelID : Int = 1
    private var userID : Int = 0
    var userName by mutableStateOf("")
    var isFollow by mutableStateOf(false)
    var reviewContent : List<ReviewContent> by mutableStateOf(listOf())
    var isUserLoggedIn by mutableStateOf(false)

    init {
        checkLoginState()
    }

    fun getNovelDetail(id: Int) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(id, userID)
                novelID = novelDetail.id_truyen
                isFollow = novelDetail.follow
                novelDetailResponse = listOf(novelDetail)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun followNovel() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                isFollow = apiService.followNovel(novelID, userID)
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

    fun checkLoginState() {
        viewModelScope.launch {
            repository.account.collectLatest {
                if (it.isNotEmpty()) {
                    val account = it.first()
                    isUserLoggedIn = account.id!! > 0
                    userID = account.accountID
                    userName = account.displayName
                } else {
                    isUserLoggedIn = false
                    userID = 0
                    userName = "Khách"
                }
            }
        }
    }
}