package com.example.docln.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
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
//    var (tenTS, settenTS) = remember { mutableStateOf("") }
    var (ketQua, setketQua) = remember { mutableIntStateOf(0) }
//    var tenTS: String by remember { mutableStateOf("") }
//    var ketQua: Int by remember { mutableIntStateOf(0) }
    if (id != null) {
        vm.getThiSinh(id.toInt())
    }
    var maTS: Int = vm.maTS
//    settenTS(vm.tenTS)
//    setketQua(vm.ketQua)
//    tenTS = vm.tenTS
//    ketQua = vm.ketQua

    Column {
        Row {
            Text(text = "Ma TS: ")
//            TextField(value = maTS.toString(), onValueChange = { maTS = it.toInt() } )
            Text(text = maTS.toString())
        }
        Row {
            Text(text = "Ten TS")
            TextField(
                value = vm.tenTS,
                onValueChange = {
                    vm.changeName(it)
//                    settenTS(it)
                } )
        }
        Row {
            Text(text = "Ket Qua")
            TextField(value = ketQua.toString(), onValueChange = { setketQua(it.toInt()) } )
        }
        Row {
            Button(onClick = {
                vm.updateThiSinh(ketQua)
                navController.popBackStack()
            }) {
                Text(text = "Cap nhat")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Tro ve")
            }
        }
    }
}