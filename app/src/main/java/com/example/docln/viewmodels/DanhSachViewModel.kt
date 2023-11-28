package com.example.docln.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docln.plugins.DBRepository
import com.example.docln.plugins.Graph
import com.example.docln.plugins.ThiSinh
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DanhSachViewModel: ViewModel() {
    private val repository: DBRepository = Graph.repository
    var dsThiSinh: List<ThiSinh> by mutableStateOf(listOf())

    init {
//        createDS()
        getDSThiSinh()
    }

    fun getDSThiSinh() {
        viewModelScope.launch {
            repository.thiSinhs.collectLatest {
                if (it.isNotEmpty()) {
                    dsThiSinh = it
                }
            }
        }
    }

    fun createDS() {
        val ds: List<ThiSinh> = listOf(
            ThiSinh(1, "Nguyễn Văn A", 9),
            ThiSinh(2, "Trương Tiến Đạt", 10),
            ThiSinh(3, "Lê Tuấn Anh", 8),
            ThiSinh(4, "Trương Thu Trang", 7),
            ThiSinh(5, "Nguyễn Phương Dung", 6),
            ThiSinh(6, "Phạm Phương Liên", 9),
        )
        viewModelScope.launch {
            for (thisinh in ds) {
                repository.insertOrUpdateThiSinh(thisinh)
            }
        }
    }
}