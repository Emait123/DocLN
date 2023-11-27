package com.example.docln.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.NovelDetail
import com.example.docln.ReviewContent
import com.example.docln.plugins.AppDataStore
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomNovel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NovelViewModel(
    private val state: SavedStateHandle
) : ViewModel() {
    private val repository: DBRepository = Graph.repository
    var novelDetailResponse: List<NovelDetail> by mutableStateOf(listOf())
    private set
    var errorMessage: String by mutableStateOf("")
    private var novelID : Int = 0
    private lateinit var dataStore: AppDataStore

//    private val state: SavedStateHandle = state
    private var userID : Int = 0
    var userName by mutableStateOf("")
    var isFollow by mutableStateOf(false)
    var reviewContent : List<ReviewContent> by mutableStateOf(listOf())
    var isUserLoggedIn by mutableStateOf(false)

    init {
//        checkLoginState()
//        getNovelDetail()
    }

    fun getNovelDetail(id: Int) {
        val id_string = checkNotNull(state["novelID"]).toString()
        println("ID la: $id_string")
//        val id = id_string.toInt()
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(id, userID)
                //Luu vao Room de xem offline
                updateNovelToRoom(novelDetail)
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
            val userInfo = dataStore.getUserInfo()
            if (userInfo.userID != -1) {
                isUserLoggedIn = true
                userID = userInfo.userID
                userName = userInfo.userName
            }
//            repository.account.collectLatest {
//                if (it.isNotEmpty()) {
//                    val account = it.first()
//                    isUserLoggedIn = account.id!! > 0
//                    userID = account.accountID
//                    userName = account.displayName
//                } else {
//                    isUserLoggedIn = false
//                    userID = 0
//                    userName = "Khách"
//                }
//            }
        }
    }

    private fun updateNovelToRoom(novel: NovelDetail) {
        viewModelScope.launch {
            repository.insertOrUpdateNovel(
                RoomNovel(
                    id_truyen = novel.id_truyen,
                    ten_truyen = novel.ten_truyen,
                    tomtat = novel.tomtat,
                    coverImg = novel.coverImg,
                    tacgia = novel.tacgia,
                    minhhoa = novel.minhhoa,
                    tag = novel.tag,
                    trangthai = novel.trangthai,
                    view = novel.view,
                    tenkhac = novel.tenkhac
                )
            )
        }
    }

    fun createDataStore(context: Context) {
        dataStore = AppDataStore(context)
    }
}