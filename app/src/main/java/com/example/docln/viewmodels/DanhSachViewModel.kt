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

    fun createDS()
}