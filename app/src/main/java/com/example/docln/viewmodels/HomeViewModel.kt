package com.example.docln.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.NovelDetail
import com.example.docln.plugins.AppDataStore
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomAccount
import com.example.docln.plugins.RoomNovel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    lateinit var userAccount: RoomAccount
    var errorMessage: String by mutableStateOf("")
    var isUserLoggedIn by mutableStateOf(false)
    var userID by mutableIntStateOf(0)
    var userName by mutableStateOf("")
    lateinit var dataStore: AppDataStore

    init {
        getNovelList()
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
//                    userAccount = account
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

    fun logOut() {
        viewModelScope.launch {
            dataStore.updateUserInfo(-1, "Khách")
            isUserLoggedIn = false
            userID = -1
            userName = "Khách"
//            repository.logOut(userAccount)
        }
    }

    fun getNovelList() {
        viewModelScope.launch {
            //Lấy ds novel từ web
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelList = apiService.getNovelList()
//                if (novelList.isEmpty()) { novelListResponse. = novelRepository.getAllNovel }
                novelListResponse = novelList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("e", errorMessage)
            }
        }
    }

    fun createDataStore(context: Context) {
        dataStore = AppDataStore(context)
    }

//    fun fetchFromDB() : Flow<List<RoomNovel>> {
//        return novelRepository.getAllNovel
//    }
}