package com.example.docln.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.docln.viewmodels.ChapterViewModel

@Composable
fun ChapterScreen(chapterID: String?) {
    val viewModel = viewModel<ChapterViewModel>()
    if (chapterID != null) {
        viewModel.ChapContent(chapterID.toInt())
    }
    var res = viewModel.chapContentResponse
    if (res.isEmpty()){
        return
    }
    var chap = res.first().noidung

    var isVisible = remember { mutableStateOf(true) }
    var backgroundColor = remember { mutableStateOf(Color.White) }
    var fontColor = remember { mutableStateOf(Color.Black) }
    var fontSize = remember { mutableStateOf(24) }
    var fontStyle = remember { mutableStateOf(FontFamily.Default) }

    val imgRegex = """`img`""".toRegex()
    var result: List<String> = chap.split(imgRegex)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .background(
            color = backgroundColor.value
        )) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .clickable { isVisible.value = !isVisible.value })
        {
            for (p in result) {
                if (p.startsWith("link:")){
                    AsyncImage(
                        model = p.removePrefix("link:"),
                        contentDescription = "null",
                    )
                    Log.e("test",p.removePrefix("link:"))
                }
                else {
                    Text(
                        text = p,
                        style = TextStyle(
                            fontSize = fontSize.value.sp,
                            color = fontColor.value,
                            fontFamily = fontStyle.value
                        )
                    )
//                    Log.e("test","Text line")
                }
            }
        }
        if (isVisible.value) {
            Row(modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Blue))
            {
                CustomReader(backgroundColor, fontColor, fontSize, fontStyle)
            }
        }
    }
}

@Composable
fun CustomReader(background : MutableState<Color>,
                 fontColor : MutableState<Color>,
                 fontSize: MutableState<Int>,
                 fontStyle: MutableState<SystemFontFamily>
) {
    //Giao dien
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (background.value == Color.White) {
                background.value = Color.Black
                fontColor.value = Color.White
            }
            else {
                background.value = Color.White
                fontColor.value = Color.Black
            }
        }) {
            Text("Change theme")
        }
        Row {
            Button(onClick = { fontSize.value-- }) {
                Text("-")
            }
            Text(fontSize.value.toString())
            Button(onClick = { fontSize.value++ }) {
                Text("+")
            }
        }
        Row {
            Button(onClick = { fontStyle.value = FontFamily.Serif }) {
                Text("Serif")
            }
            Button(onClick = { fontStyle.value = FontFamily.Cursive }) {
                Text("Cursive")
            }
            Button(onClick = { fontStyle.value = FontFamily.Monospace }) {
                Text("Monospace")
            }
        }
    }
}