package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomAccount
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var isUserLogin : Boolean by mutableStateOf(false)
        private set
    var response: String by mutableStateOf("")
    var userName: String by mutableStateOf("")
    var userID: String by mutableStateOf("")

    fun checkUser(loginName : String, password : String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.loginUser(loginName, password)
                if (res.responseMessage == "ok") {
                    val account = RoomAccount(accountID = res.id, displayName = res.displayName)
                    repository.logIn(account)
                    response = "Đăng nhập thành công!"
                    isUserLogin = true
                }
                if (res.responseMessage == "fail") {
                    response = "Thông tin đăng nhập sai"
                }
            }
            catch (e: Exception) {
                response = e.message.toString()
            }
        }
    }
}