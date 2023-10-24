package com.example.docln.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.LoginResponse
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: DBRepository = Graph.repository
) : ViewModel() {
    var isUserLogin : Boolean by mutableStateOf(false)
        private set
    var errorMessage: String by mutableStateOf("")
    var userName: String by mutableStateOf("")
    var userID: String by mutableStateOf("")
//    private lateinit var auth: FirebaseAuth
//    var auth = FirebaseAuth.getInstance()

    fun checkUser(loginName : String, password : String) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.loginUser(loginName, password)
                if (res.responseMessage == "ok") {
                    val account = RoomAccount(accountID = res.id, displayName = res.displayName)
                    repository.logIn(account)
                    isUserLogin = true
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}