package com.example.docln.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.LoginResponse
import com.example.docln.plugins.RetrofitAPI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    var isUserLogin : Boolean by mutableStateOf(false)
        private set
    var errorMessage: String by mutableStateOf("")
    var userName: String by mutableStateOf("")
    var userID: String by mutableStateOf("")
//    private lateinit var auth: FirebaseAuth
//    var auth = FirebaseAuth.getInstance()

    fun checkUser(loginName : String, password : String) {
        val login = LoginResponse(loginName, password)
        println(login)

        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val res = apiService.loginUser(loginName, password)
                println(res)
                if (res.responseMessage == "ok") {
                    isUserLogin = true
                    userName = res.displayName.toString()
                    userID = res.userId
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}