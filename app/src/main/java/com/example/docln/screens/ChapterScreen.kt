package com.example.docln.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.docln.R
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
    var textAlign = remember { mutableStateOf(TextAlign.Justify) }

    val imgRegex = """`img`""".toRegex()
    val result: List<String> = chap.split(imgRegex)

    val interactionSource = MutableInteractionSource()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor.value),
    )
    {
        Column (
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { isVisible.value = !isVisible.value }
                ))
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
                        textAlign = textAlign.value,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                }
            }
        }
        if (isVisible.value) {
            BottomNavBar(
                backgroundColor,
                fontSize, fontStyle,
                textAlign,
                Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    //Thêm sự kiện click rỗng để thanh navbar không bị đóng khi bấm nhầm vào khoảng trống giữa các nút
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {}
                    )
            )
//            Row(modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .background(Color.White)
//                )
//            {
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    background : MutableState<Color>,
    fontSize: MutableState<Int>,
    fontStyle: MutableState<SystemFontFamily>,
    textAlign: MutableState<TextAlign>,
    navBarModifier: Modifier
) {
    Box(modifier = navBarModifier) {
        var showCustomText = remember { mutableStateOf(false) }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(0f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            val iconModifier = Modifier.size(40.dp)
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
                    .clickable { showCustomText.value = !showCustomText.value }
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

//        val bottomState = remember
//        ModalDrawerSheet (
//            modifier = Modifier,
//            content = CustomReader(
//                background = background,
//                fontSize = fontSize,
//                fontStyle = fontStyle,
//                textAlign = textAlign
////                show =
//            )
//        )
        if (showCustomText.value) {
            CustomReader(background = background, fontSize = fontSize, fontStyle = fontStyle, textAlign = textAlign, showCustomText)
//            Column (modifier = Modifier.fillMaxSize().align(Alignment.Center).zIndex(2f)) {
//            }
        }
    }
}

@Composable
fun CustomReader(
    background : MutableState<Color>,
    fontSize: MutableState<Int>,
    fontStyle: MutableState<SystemFontFamily>,
    textAlign: MutableState<TextAlign>,
    show: MutableState<Boolean>
){
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = 0.6f)
        .padding(bottom = 40.dp),
        verticalArrangement = Arrangement.SpaceAround)
    {
        val iconBoxModifier = Modifier
            .size(width = 100.dp, height = 50.dp)
            .padding(10.dp)

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Icon(
                Icons.Rounded.Close,
                contentDescription = null,
                iconBoxModifier
                    .clickable { show.value = !show.value }
                    .padding(end = 10.dp)
            )
        }

        val textModifier = Modifier.padding(start = 20.dp)
        Text(text = "Màu nền", modifier = textModifier)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val colorBlockModifier = iconBoxModifier.shadow(10.dp)
            Canvas(
                modifier = colorBlockModifier
                    .clickable { background.value = Color.Yellow },
                onDraw = { drawRect(Color.Yellow) }
            )
            Canvas(
                modifier = colorBlockModifier.clickable { background.value = Color.Red },
                onDraw = { drawRect(Color.Red) }
            )
            Canvas(
                modifier = colorBlockModifier.clickable { background.value = Color.Blue },
                onDraw = { drawRect(Color.Blue) }
            )
            Canvas(
                modifier = colorBlockModifier.clickable { background.value = Color.White },
                onDraw = { drawRect(Color.White) }
            )
        }
        Text("Font chữ", modifier = textModifier)
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            var showFontList by remember { mutableStateOf(false) }
            val fontList: List<SystemFontFamily> = listOf(FontFamily.Default, FontFamily.Serif, FontFamily.Cursive, FontFamily.Monospace)
            Button(onClick = { showFontList = true }) {
                Text("Show font list")
            }
            DropdownMenu(
                expanded = showFontList,
                onDismissRequest = { showFontList = false }
            )
            {
                for (font in fontList) {
                    DropdownMenuItem(
                        text = { Text(font.toString().removePrefix("FontFamily.")) },
                        onClick = { fontStyle.value = font })
                }
            }
        }
        Text("Kích cỡ chữ", modifier = textModifier)
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Icon(
                Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { fontSize.value-- }
            )
            Text(text = fontSize.value.toString())
            Icon(
                Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { fontSize.value++ }
            )
        }
        Text("Căn lề", modifier = textModifier)
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = R.drawable.format_align_left_24px),
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { textAlign.value = TextAlign.Left }
            )
            Icon(
                painter = painterResource(id = R.drawable.format_align_center_24px),
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { textAlign.value = TextAlign.Center }
            )
            Icon(
                painter = painterResource(id = R.drawable.format_align_right_24px),
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { textAlign.value = TextAlign.Right }
            )
            Icon(
                painter = painterResource(id = R.drawable.format_align_justify_24px),
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { textAlign.value = TextAlign.Justify }
            )
        }
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
//        CustomReader()
    }
}

