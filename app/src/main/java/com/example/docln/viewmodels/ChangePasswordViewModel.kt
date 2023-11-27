package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var changePasswordSuccess: Boolean by mutableStateOf(false)
        private set
    var response: String by mutableStateOf("")

    fun changePassword(loginName: String, oldPassword: String, newPassword: String) {
        viewModelScope.launch {
//            println(loginName)
//            println(oldPassword)
            println(newPassword)

            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.changePassword(loginName, oldPassword, newPassword)
                if (res.responseMessage == "success") {
                    changePasswordSuccess = true
                    response = "Đổi mật khẩu thành công"
                } else {
                    response = "thất bại"
                }
            } catch (e: Exception) {
                response = e.message.toString()
            }
        }
    }
}
