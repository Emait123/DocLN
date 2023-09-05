package com.example.docln.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.Chapter
import com.example.docln.NovelDetail
import com.example.docln.viewmodels.NovelViewModel
import com.example.docln.R
import com.example.docln.ui.theme.DocLNTheme

class NovelActivity : ComponentActivity() {

    private val viewModel by viewModels<NovelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocLNTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("hi")
//                    viewModel.getNovelDetail(1)
//                    val res = viewModel.novelDetailResponse.firstOrNull()
//                    if (res != null) {
//                        NovelDetail(res)
                }
            }
        }
    }
}



//@Composable
//fun NovelDetail(novel : NovelDetail) {
//    val context = LocalContext.current
//    val activity = context.findActivity()
//    val intent = activity?.intent
//    val id = intent?.getIntExtra("novelID", 1)
//
//    Column (modifier = Modifier.fillMaxWidth()) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(R.drawable.nocover)
////                .data(novel.coverImg)
//                .crossfade(true)
//                .build(),
//            contentDescription = "Novel Cover Image",
//            error = painterResource(id = R.drawable.nocover),
//            modifier = Modifier
//                .clip(RoundedCornerShape(10.dp))
//                .align(CenterHorizontally)
//                .padding(10.dp)
//        )
//        Text(
//            style = MaterialTheme.typography.titleMedium,
//            text = novel.ten_truyen,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.align(CenterHorizontally)
//        )
//        Text(
//            text = "Tác giả: ",
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
//                .align(Alignment.Start),
//        )
//        Text(
//            text = "Minh họa: ",
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 10.dp, bottom = 10.dp),
//        )
//        Text(
//            text = "Tag: ",
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 10.dp, bottom = 10.dp),
//        )
//        Text(
//            text = "Tình trạng: ",
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 10.dp, bottom = 10.dp),
//        )
//        Row (
//            modifier = Modifier.padding(10.dp).fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround) {
//            Column (horizontalAlignment = CenterHorizontally) {
//                Icon(
//                    Icons.Rounded.FavoriteBorder,
//                    contentDescription = "Yêu thích"
//                )
//                Text("1234")
//            }
//            Column (horizontalAlignment = CenterHorizontally) {
//                Icon(
//                    Icons.Rounded.Star,
//                    contentDescription = "Đánh giá"
//                )
//                Text("Đánh giá")
//            }
//            Column (horizontalAlignment = CenterHorizontally) {
//                Icon(
//                    Icons.Rounded.Share,
//                    contentDescription = "Chia sẻ"
//                )
//                Text("Chia sẻ")
//            }
//        }
//        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
//        Text(text = "Cập nhật lần cuối: 18 giờ", modifier = Modifier.align(CenterHorizontally))
//        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
//    }
////        LazyColumn {
////            itemsIndexed(items = truyen.dsChuong, key = {index, chapter -> chapter.id_chuong}) {
////                    index, item -> ChapterList(chapter = item)
////            }
////        }
//}

//@Composable
//fun ChapterList(chapter: Chapter, modifier: Modifier = Modifier) {
//    Row {
//        Text(
//            text = chapter.STT.toString(),
//            modifier = modifier
//        )
//        Text(text = chapter.ten_chuong)
//    }
//}


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DocLNTheme {
//        val viewModel = NovelViewModel()
//        viewModel.getNovelDetail(1)
//        val res = NovelDetail(0, "Default name", "")
//        val res = viewModel.novelDetailResponse.firstOrNull()
//        NovelDetail(res)
        Text("hi")
    }
}