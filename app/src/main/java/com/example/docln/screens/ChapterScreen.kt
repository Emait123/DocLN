package com.example.docln.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.docln.Chapter
import com.example.docln.ui.theme.DocLNTheme
import com.example.docln.viewmodels.ChapterViewModel

@Composable
fun ChapterScreen(chapterID: String?) {
    val viewModel = viewModel<ChapterViewModel>()
    if (chapterID != null) {
        viewModel.ChapContent(chapterID.toInt())
    }
    val res = viewModel.chapContentResponse
    if (res.isEmpty()){
        return
    }
    val content = res.first()
    val chap = content.noidung

    val curChap = content.STT

    var isVisible = remember { mutableStateOf(false) }
    var backgroundColor = remember { mutableStateOf(Color.White) }
    var fontColor = remember { mutableStateOf(Color.Black) }
    var fontSize = remember { mutableStateOf(24) }
    var fontStyle = remember { mutableStateOf(FontFamily.Default) }

    val imgRegex = """`img`""".toRegex()
    val result: List<String> = chap.split(imgRegex)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .background(
            color = backgroundColor.value
        )) {
        Column (
            modifier = Modifier
//                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .clickable { isVisible.value = !isVisible.value })
        {
            Text(
                text = content.ten_chuong,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .size(40.dp)
            )

            for (p in result) {
                if (p.startsWith("link:")){
                    AsyncImage(
                        model = p.removePrefix("link:"),
                        contentDescription = "null",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else {
                    Text(
                        text = p,
                        style = TextStyle(
                            fontSize = fontSize.value.sp,
                            color = fontColor.value,
                            fontFamily = fontStyle.value,
                        ),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    )
//                    Log.e("test","Text line")
                }
            }
        }
        if (isVisible.value) {
            Row(modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .height(60.dp))
            {
                BottomNavBar(Modifier.size(40.dp).fillMaxSize())
//                CustomReader(backgroundColor, fontColor, fontSize, fontStyle)
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

@Composable
fun BottomNavBar(iconModifier: Modifier = Modifier) {
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
        Icon(
            Icons.Rounded.Home,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
        Icon(
            Icons.Rounded.Settings,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
        Icon(
            Icons.Rounded.Info,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
        Icon(
            Icons.Rounded.AddCircle,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
        Icon(
            Icons.Rounded.ArrowForward,
            contentDescription = null,
            modifier = iconModifier
                .clickable {  }
        )
    }
}

//inline fun Modifier.checkSTT(curChap: Int, dsChap: List<Chapter>, modifier: Modifier.() -> Modifier) : Modifier {
//    return if (dsChap.none { it.STT < curChap }) {
//        then(modifier(Modifier))
//    } else {
//        this
//    }
//}

@Composable
fun test(){
    Text("hi")
}

@Preview (showBackground = true)
@Composable
fun PreviewScreen() {
    DocLNTheme {
        test()
    }
}

