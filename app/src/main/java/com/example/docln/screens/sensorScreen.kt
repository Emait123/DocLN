package com.example.docln.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun sensorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("sensor") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val (name, setName) = remember { mutableStateOf("") }
                    val (date, setdate) = remember { mutableStateOf("") }
                    val (temp, setTemp) = remember { mutableStateOf("") }

                    TextField(
                        value = name,
                        onValueChange = { setName(it) },
                        label = { Text("họ tên") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    TextField(
                        value = date,
                        onValueChange = { setdate(it) },
                        label = { Text("ngày tháng năm và thời gian") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    TextField(
                        value = temp,
                        onValueChange = { setTemp(it) },
                        label = { Text("nhiệt độ") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray
                        )
                    )


                    OutlinedButton(
                        onClick = {
//                            viewModel.changePassword(username, password,newpassword)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Xác Nhận")
                    }
                }
            }
        }
    )
}