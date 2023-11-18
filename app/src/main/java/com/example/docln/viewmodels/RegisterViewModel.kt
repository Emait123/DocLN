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

//class RegisterViewModel(
//    private val repository: DBRepository = Graph.repository
//) : ViewModel() {
//    var errorMessage: String by mutableStateOf("")
//
//    fun registerUser( displayName: String,loginName : String, password : String) {
//        viewModelScope.launch {
//            val apiService = RetrofitAPI.getInstance()
//            try {
//                val res = apiService.registerUser(displayName,loginName, password,)
//                if (res.responseMessage == "ok") {
//                    val account = RoomAccount(accountID = res.id, displayName = res.displayName)
//                    repository.logIn(account)
//                }
//            }
//            catch (e: Exception) {
//                errorMessage = e.message.toString()
//            }
//        }
//    }
//}

class RegisterViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var errorMessage: String by mutableStateOf("")
    var registrationSuccess: Boolean by mutableStateOf(false)

    fun registerUser(displayName: String, loginName: String, password: String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.registerUser(displayName, loginName, password)
                if (res.responseMessage == "ok") {
                    val account = RoomAccount(accountID = res.id, displayName = res.displayName)
                    repository.logIn(account)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
