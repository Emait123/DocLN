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
    var response: String by mutableStateOf("")
    var registrationSuccess: Boolean by mutableStateOf(false)
        private set

    fun isValidInput(displayName: String, loginName: String, password: String): Boolean {
        if (displayName.isNotBlank() && loginName.isNotBlank() && password.length >= 6)
            return true
        else {
            response = "Vui lòng nhập đầy đủ thông tin và mật khẩu phải có ít nhất 6 ký tự."
            return false
        }
    }

    fun registerUser(displayName: String, loginName: String, password: String) {
        if (!isValidInput(displayName, loginName, password))
            return
        else {
            viewModelScope.launch {
                val apiService = RetrofitAPI.getInstance()
                try {
                    val res = apiService.registerUser(displayName, loginName, password)
                    if (res.responseMessage == "ok") {
                        val account = RoomAccount(accountID = res.id, displayName = res.displayName)
                        repository.logIn(account)
                        response = "Đăng ký thành công"
                        registrationSuccess = true
                    }
                    else if (res.responseMessage == "dup") {
                        response = "Tài khoản này đã tồn tại"
                    }
                    else {
                        response = "Đăng ký thất bại"
                    }
                } catch (e: Exception) {
                    response = e.message.toString()
                }
            }
        }
    }
}
