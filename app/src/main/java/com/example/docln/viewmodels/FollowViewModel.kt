package com.example.docln.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FollowViewModel (
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    private var isUserLoggedIn : Boolean = false
    private var userID : Int = 0
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    init {
        checkLoginState()
        println(userID)
//        getFollowList()
//        println(novelListResponse)
    }

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

    private fun checkLoginState() {
        viewModelScope.launch {
            repository.account.collectLatest {
                if (it.isNotEmpty()) {
                    val account = it.first()
                    isUserLoggedIn = account.id!! > 0
                    userID = account.accountID
                } else {
                    isUserLoggedIn = false
                    userID = 0
                }
            }
        }
    }
}