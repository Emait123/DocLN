package com.example.docln.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.Chapter
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.viewmodels.NovelViewModel

@Composable
fun NovelDetailScreen(navController: NavController, novelID: String?) {
//    val navController = rememberNavController()
    val viewModel = viewModel<NovelViewModel>()
    if (novelID != null) {
        viewModel.getNovelDetail(novelID.toInt())
    }
    var res = viewModel.novelDetailResponse
    if (res.isEmpty()){
        return
    }
    var novel = res.first()
    val dsChuong = novel.dsChuong.sortedBy { it.STT }
    println("in screen:")
    println(dsChuong)
    Column (modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.nocover)
//                .data(novel.coverImg)
                .crossfade(true)
                .build(),
            contentDescription = "Novel Cover Image",
            error = painterResource(id = R.drawable.nocover),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = novel.ten_truyen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Tác giả: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .align(Alignment.Start),
        )
        Text(
            text = "Minh họa: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, bottom = 10.dp),
        )
        Text(
            text = "Tag: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, bottom = 10.dp),
        )
        Text(
            text = "Tình trạng: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, bottom = 10.dp),
        )
        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.FavoriteBorder,
                    contentDescription = "Yêu thích"
                )
                Text("1234")
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "Đánh giá"
                )
                Text("Đánh giá")
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Share,
                    contentDescription = "Chia sẻ"
                )
                Text("Chia sẻ")
            }
        }
        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        Text(text = "Cập nhật lần cuối: 18 giờ", modifier = Modifier.align(Alignment.CenterHorizontally))
        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        Text("Danh sách chương", modifier = Modifier.padding(10.dp))
        LazyColumn (modifier = Modifier.padding(10.dp)) {
            itemsIndexed(items = dsChuong, key = {index, chapter -> chapter.id_chuong}) {
                    index, item -> ChapterList(navController, chapter = item)
            }
        }
    }
}

@Composable
fun ChapterList(navController: NavController, chapter: Chapter) {
    Row (modifier = Modifier.fillMaxWidth().clickable {
        navController.navigate(Routes.Chapter.withArgs(chapter.id_chuong.toString()))
    }) {
        Text(
            text = chapter.STT.toString(),
            modifier = Modifier
        )
        Text(text = chapter.ten_chuong)
    }
}