package com.example.docln.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.AppDataStore
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FollowViewModel (
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    private lateinit var dataStore: AppDataStore
    private var isUserLoggedIn : Boolean = false
    private var userID : Int = 0
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getFollowList() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                novelListResponse = apiService.getFollowList(userID)
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
            }
//            repository.account.collectLatest {
//                if (it.isNotEmpty()) {
//                    val account = it.first()
//                    isUserLoggedIn = account.id!! > 0
//                    userID = account.accountID
//                } else {
//                    isUserLoggedIn = false
//                    userID = 0
//                }
//            }
        }
    }

    fun createDataStore(context: Context) {
        dataStore = AppDataStore(context)
    }
}