package com.example.docln.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.docln.viewmodels.ChiTietViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChiTietScreen(navController: NavController, id: String?) {
    val vm = viewModel<ChiTietViewModel>()
    if (id != null) {
        vm.getThiSinh(id.toInt())
    }
    var maTS: Int = vm.maTS
    var tenTS: String = vm.tenTS
    var ketQua: Int = vm.ketQua
    Column {
        Row {
            Text(text = "Ma TS")
            TextField(value = maTS.toString(), onValueChange = { maTS = it.toInt() } )
        }
        Row {
            Text(text = "Ten TS")
            TextField(value = tenTS, onValueChange = { tenTS = it } )
        }
        Row {
            Text(text = "Ket Qua")
            TextField(value = ketQua.toString(), onValueChange = { ketQua = it.toInt() } )
        }
    }
}