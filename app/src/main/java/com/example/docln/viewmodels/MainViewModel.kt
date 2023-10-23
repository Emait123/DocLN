package com.example.docln.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.Novel
import com.example.docln.plugins.NovelRepository
import com.example.docln.plugins.RetrofitAPI
import com.example.docln.plugins.RoomNovel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    var novelListResponse:List<Novel> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
//    private val novelRepository : NovelRepository = NovelRepository(application)

    fun getNovelList() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelList = apiService.getNovelList()
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