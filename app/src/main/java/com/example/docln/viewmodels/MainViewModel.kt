package com.example.docln.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomAccount
import com.example.docln.plugins.RoomNovel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    lateinit var userAccount: RoomAccount
    var errorMessage: String by mutableStateOf("")
    var isUserLoggedIn by mutableStateOf(false)
    var userID by mutableIntStateOf(0)
    var userName by mutableStateOf("")
//    private val novelRepository : NovelRepository = NovelRepository(appObj)

    fun checkLoginState() {
        viewModelScope.launch {
            repository.account.collectLatest {
                if (it.isNotEmpty()) {
                    val account = it.first()
                    userAccount = account
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

    fun logOut() {
        viewModelScope.launch {

            repository.logOut(userAccount)
        }
    }

    fun getNovelList() {
        viewModelScope.launch {
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

//    fun fetchFromDB() : Flow<List<RoomNovel>> {
//        return novelRepository.getAllNovel
//    }
}