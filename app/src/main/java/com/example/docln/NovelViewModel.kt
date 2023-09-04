package com.example.docln

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NovelViewModel(
//    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
//    var novelDetailResponse: MutableLiveData<NovelDetail> = MutableLiveData()
//    private val _res = MutableStateFlow<NovelDetail?>(null)
//    val novelDetailResponse = _res.asStateFlow()
//    val novelDetailResponse:StateFlow<NovelDetail> = savedStateHandle.getStateFlow("detail", NovelDetail(0,"default",""))
    var novelDetailResponse by mutableStateOf("")
    private set
    var errorMessage: String by mutableStateOf("")

    fun getNovelDetail() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(2)
                println("in view model")
                println(novelDetail)
                novelDetailResponse = novelDetail.ten_truyen
                println("after view model")
                println(novelDetailResponse)
//                novelDetailResponse.clear()
//                novelDetailResponse.add(novelDetail)
//                savedStateHandle["detail"] = novelDetail
//                novelDetailResponse.value.ten_truyen = novelDetail.ten_truyen
//                novelDetailResponse.value = novelDetail
//                _res.value = novelDetail
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}