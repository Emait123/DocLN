package com.example.docln.screens

import android.media.MediaPlayer
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.docln.ChapterContent
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.ui.theme.DocLNTheme
import com.example.docln.viewmodels.ChapterViewModel

//Màn hình hiển thị nội dung chương truyện
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterScreen(
//    viewModel: ChapterViewModel,
    navController : NavController,
    novelID: String?, chapterID: String?) {
    val viewModel = viewModel<ChapterViewModel>()
    println("novel: $novelID, chapter: $chapterID")
    if (chapterID != null && novelID != null) {
        viewModel.chapContent(novelID.toInt(), chapterID.toInt())
    }
    val res = viewModel.chapContentResponse
    if (res.isEmpty()){
        return
    }
    val content = res.first()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(
                    text = content.ten_chuong,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                ) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                navigationIcon = {
                    IconButton(onClick = {
                        // Thêm args để quay về trang nội dung truyện dù trong backstack có bao nhiêu chương truyện
                        navController.popBackStack(Routes.NovelDetail.withArgs(content.id_truyen.toString()), false) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            ) },
        content = { paddingValues -> ChapterScreenContent(navController, Modifier.padding(paddingValues), content) }
    )
}

@Composable
fun ChapterScreenContent(navController: NavController, modifier: Modifier, content : ChapterContent) {

    val chapterViewModel = viewModel<ChapterViewModel>()
    var isVisible by remember { mutableStateOf(false) }
    val imgRegex = """`img`""".toRegex()
    val result: List<String> = content.noidung.split(imgRegex)

    chapterViewModel.createDataStore(LocalContext.current)
    val fontColorList = listOf(Color.Black, Color.White)
    val bgColorList = listOf(colorResource(id = R.color.nau), colorResource(id = R.color.hong), Color.Black, Color.White)
    val fontList = listOf(FontFamily.Default, FontFamily.Serif, FontFamily.Cursive, FontFamily.Monospace)
    val textAlignList = listOf(
        Pair(R.drawable.format_align_left_24px, TextAlign.Left),
        Pair(R.drawable.format_align_center_24px, TextAlign.Center),
        Pair(R.drawable.format_align_right_24px, TextAlign.Right),
        Pair(R.drawable.format_align_justify_24px, TextAlign.Justify))

    val interactionSource = MutableInteractionSource()
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = bgColorList[chapterViewModel.backgroundColor]),
    )
    {
        Column (
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { isVisible = !isVisible }
                ))
        {
            Text(
                text = content.ten_chuong,
                style = TextStyle(
                    color = fontColorList[chapterViewModel.fontColor],
                    fontFamily = fontList[chapterViewModel.fontStyle],
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
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
                            fontSize = chapterViewModel.fontSize.sp,
                            color = fontColorList[chapterViewModel.fontColor],
                            fontFamily = fontList[chapterViewModel.fontStyle],
                        ),
                        textAlign = textAlignList[chapterViewModel.textAlign].second,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                }
            }
        }
        if (isVisible) {
            BottomNavBar(
                navController = navController,
                content = content,
                Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    //Thêm sự kiện click rỗng để thanh navbar không bị đóng khi bấm nhầm vào khoảng trống giữa các nút
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {})
            )
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    content : ChapterContent,
    modifier: Modifier
) {
    val curChapID = content.id_chuong
    val curChapSTT = content.STT
    val chapNum = content.dsChuong.count()

    val chapterViewModel = viewModel<ChapterViewModel>()
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }


    Box(modifier = modifier) {
        var showCustomText by remember { mutableStateOf(false) }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(0f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            val iconModifier = Modifier.size(40.dp)
            IconButton(
                enabled = curChapSTT > 1,
                onClick = {
                    val prevChapID = content.dsChuong[curChapSTT-2].id_chuong
                    navController.navigate(Routes.Chapter.withArgs(content.id_truyen.toString(), prevChapID.toString()))
                }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = iconModifier,
                )
            }
            Icon(
                Icons.Rounded.Home,
                contentDescription = null,
                modifier = iconModifier
                    .clickable { navController.popBackStack(Routes.NovelDetail.withArgs(content.id_truyen.toString()), false) }
            )
            Icon(
                Icons.Rounded.Settings,
                contentDescription = null,
                modifier = iconModifier
                    .clickable { showCustomText = !showCustomText }
            )
//            Icon(
//                Icons.Rounded.Info,
//                contentDescription = null,
//                modifier = iconModifier
//                    .clickable {  }
//            )
//            Icon(
//                Icons.Rounded.AddCircle,
//                contentDescription = null,
//                modifier = iconModifier
//                    .clickable {  }
//            )
//            IconButton(
//                onClick = {
//                    mediaPlayer?.release()
//                    mediaPlayer = MediaPlayer.create(context, R.raw.'cái này là file')
//                    mediaPlayer?.start()
//                },
//                modifier = iconModifier
//            ) {
//                Icon(
//                    Icons.Rounded.PlayArrow,
//                    contentDescription = null
//                )
//            }

            IconButton(
                enabled = curChapSTT < chapNum,
                onClick = {
                    val nextChapID = content.dsChuong[curChapSTT].id_chuong
                    navController.navigate(Routes.Chapter.withArgs(content.id_truyen.toString(), nextChapID.toString()))
                }) {
                Icon(
                    Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    modifier = iconModifier,
                )
            }
        }

        if (showCustomText) {
            CustomReader(
                onVisibilityChange = { chapterViewModel.saveReaderSettings()
                    showCustomText = !showCustomText }
            )
        }
    }
}

@Composable
fun CustomReader(
    onVisibilityChange: () -> Unit,
){
    val chapterViewModel = viewModel<ChapterViewModel>()
    val bgColorList = listOf(colorResource(id = R.color.nau), colorResource(id = R.color.hong), Color.Black, Color.White)
    val fontList = listOf(FontFamily.Default, FontFamily.Serif, FontFamily.Cursive, FontFamily.Monospace)
    val textAlignList = listOf(
        Pair(R.drawable.format_align_left_24px, TextAlign.Left),
        Pair(R.drawable.format_align_center_24px, TextAlign.Center),
        Pair(R.drawable.format_align_right_24px, TextAlign.Right),
        Pair(R.drawable.format_align_justify_24px, TextAlign.Justify))

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
                    .clickable { onVisibilityChange() }
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
            bgColorList.forEachIndexed { index, color ->
                Canvas(
                    modifier = colorBlockModifier.clickable { chapterViewModel.changeBGColor(color, index) },
                    onDraw = { drawRect(color) }
                )
            }
        }

        Text("Font chữ", modifier = textModifier)
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            var showFontList by remember { mutableStateOf(false) }

            Button(onClick = { showFontList = true }) {
                Text("Show font list")
            }
            DropdownMenu(
                expanded = showFontList,
                onDismissRequest = { showFontList = false }
            )
            {
                fontList.forEachIndexed { index, font ->
                    DropdownMenuItem(
                        text = { Text(font.toString().removePrefix("FontFamily.")) },
                        onClick = { chapterViewModel.changeFontStyle(font, index) })
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
                    .clickable { chapterViewModel.decFontSize() }
            )
            Text(text = chapterViewModel.fontSize.toString())
            Icon(
                Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                modifier = iconBoxModifier
                    .clickable { chapterViewModel.incFontSize() }
            )
        }

        Text("Căn lề", modifier = textModifier)
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            textAlignList.forEachIndexed { index, item ->
                Icon(
                    painter = painterResource(id = item.first),
                    contentDescription = null,
                    modifier = iconBoxModifier
                        .clickable { chapterViewModel.changeTextAlign(item.second, index) }
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewScreen() {
    DocLNTheme {
//        CustomReader()
    }
}

