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

class RegisterViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var errorMessage: String by mutableStateOf("")
    var registrationSuccess: Boolean by mutableStateOf(false)
        private set
    private fun isValidInput(displayName: String, loginName: String, password: String): Boolean {
        return displayName.isNotBlank() && loginName.isNotBlank() && password.length >= 6
    }
    fun registerUser(displayName: String, loginName: String, password: String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.registerUser(displayName, loginName, password)
                if (res.responseMessage == "ok") {
                    val account = RoomAccount(accountID = res.id, displayName = res.displayName)
                    repository.logIn(account)
                }else {
                    errorMessage = "Vui lòng nhập đầy đủ thông tin và mật khẩu phải có ít nhất 6 ký tự."
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
