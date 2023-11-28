package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.ThiSinh
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChiTietViewModel : ViewModel() {
    private val repository: DBRepository = Graph.repository
    var dsThiSinh: List<ThiSinh> by mutableStateOf(listOf())
    var maTS: Int by mutableIntStateOf(0)
    var tenTS: String by mutableStateOf("")
    var ketQua: Int by mutableIntStateOf(0)

    fun getThiSinh(id: Int) {
        viewModelScope.launch {
            repository.getThiSinh(id).collectLatest {
                maTS = it.id!!
                tenTS = it.tenTS
                ketQua = it.ketQua
            }
        }
    }

    fun changeName(ten:String) {
        tenTS = ten
    }
    fun changeDiem(diem: Int) {
        ketQua = diem
    }

    fun updateThiSinh(ketqua: Int) {
        viewModelScope.launch {
            repository.insertOrUpdateThiSinh(
                ThiSinh(maTS, tenTS, ketqua)
            )
        }
    }
}