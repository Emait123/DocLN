package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.NovelDetail
import com.example.docln.RetrofitAPI
import kotlinx.coroutines.launch

class NovelViewModel(
//    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
//    var novelDetailResponse: MutableLiveData<NovelDetail> = MutableLiveData()
//    private val _res = MutableStateFlow<NovelDetail?>(null)
//    val novelDetailResponse = _res.asStateFlow()
//    val novelDetailResponse:StateFlow<NovelDetail> = savedStateHandle.getStateFlow("detail", NovelDetail(0,"default",""))
//    var novelDetailResponse by mutableStateOf("")
//    var novelDetailResponse: MutableList<NovelDetail> by mutableStateOf(mutableListOf())
    var novelDetailResponse: List<NovelDetail> by mutableStateOf(listOf())
    private set
    var errorMessage: String by mutableStateOf("")

    fun getNovelDetail(id: Int) {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            try {
                val novelDetail = apiService.getNovelDetail(id)
                println("in view model")
                println(novelDetail)
//                novelDetailResponse.clear()
//                novelDetailResponse.add(novelDetail)
                novelDetailResponse = listOf(novelDetail)
                println("after view model")
                println(novelDetailResponse)
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