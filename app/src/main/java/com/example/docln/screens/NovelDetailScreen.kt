package com.example.docln.screens

import android.graphics.Paint.Style
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.util.toHalf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.Chapter
import com.example.docln.Navigation
import com.example.docln.Novel
import com.example.docln.NovelDetail
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.ui.theme.DocLNTheme
import com.example.docln.viewmodels.NovelViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.lang.Math.floor

@Composable
fun NovelDetailScreen(navController: NavController, novelID: String?) {
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

    var showDialog by remember{ mutableStateOf(false) }

    LazyColumn (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
//                    .data(R.drawable.nocover)
                    .data(novel.coverImg)
                    .crossfade(true)
                    .build(),
                contentDescription = "Novel Cover Image",
                error = painterResource(id = R.drawable.nocover),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = novel.ten_truyen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )
            Text(
                text = "Tác giả: ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                    .wrapContentWidth(align = Alignment.Start)
            )
            Text(
                text = "Minh họa: ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.Start)
                    .padding(start = 10.dp, bottom = 10.dp),
            )
            Text(
                text = "Tag: ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.Start)
                    .padding(start = 10.dp, bottom = 10.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tình trạng: ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(10.dp)
                )
                Text(
                    text = "Hoàn thành",
                    modifier = Modifier
                        .background(color = Color.Green, shape = RoundedCornerShape(10.dp))
                        .padding(5.dp)
                )
            }
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
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { showDialog = !showDialog }) {
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
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))
            Text(text = "Cập nhật lần cuối: 18 giờ", modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally))
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))

            Text(text = "Tên khác:")
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))

            ExpandDesc(novel.tomtat)
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))

            ExpandRating()
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))

            Text("Danh sách chương", modifier = Modifier.padding(10.dp))
        }

        if (dsChuong.isNotEmpty()) {
            itemsIndexed(items = dsChuong, key = {index, chapter -> chapter.id_chuong}) {
                    index, item -> ChapterList(navController, chapter = item)
            }
        }
        else {
            item { NoChapter() }
        }
    }
    if (showDialog) {
        InputRating(onDismissRequest = { showDialog = false })
    }
}

@Composable
fun StarRating(score: Double, color: Color = Color.Yellow, ratingModifier: Modifier = Modifier) {
    //Mặc định số sao là 5
    val filledStars = (score).toInt()
    val halfStar = kotlin.math.floor(score) < score
    val emptyStars = kotlin.math.floor(5 - score).toInt()

    Row(modifier = ratingModifier) {
        repeat (filledStars) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = color
            )
        }
        if (halfStar) {
            Icon(
                painter = painterResource(id = R.drawable.star_half_24px),
                contentDescription = null,
                tint = color
            )
        }
        repeat(emptyStars) {
            Icon(
                painter = painterResource(id = R.drawable.star_outline_24px),
                contentDescription = null,
                tint = color)
        }
    }
}

@Composable
fun ChapterList(navController: NavController, chapter: Chapter) {
    Row (
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate(Routes.Chapter.withArgs(chapter.id_chuong.toString()))
            }) {
        Text(
            modifier = Modifier
//                .size(30.dp)
                .weight(0.1f),
            text = chapter.STT.toString()
        )
        Text(
            modifier = Modifier
//                .size(20.dp)
                .weight(0.9f),
            text = chapter.ten_chuong,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun NoChapter() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Rounded.Warning,
            contentDescription = "Khong co chuong"
        )
        Text("Không có chương nào")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandDesc(desc : String) {
    var expanded by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .animateContentSize(),
        onClick = { expanded = !expanded }
    ) {
        Column {
            Text("Tóm tắt:")
            if (expanded) {
                Text(desc)
            } else {
                Text(desc.substring(0, 70) + "...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandRating() {
    var expanded by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .animateContentSize(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
//        elevationOverlay = Color.White,
        onClick = { expanded = !expanded }
    ) {
        Column (modifier = Modifier.fillMaxWidth()) {
            Row (){
                Icon(Icons.Rounded.Star, contentDescription = null)
                Text("Đánh giá: ")
                StarRating(score = 3.4, color = Color.Yellow, Modifier.padding(horizontal = 10.dp))
                Text("(3.4)")
            }
            if (expanded) {
                Column {
                    Text("Đánh giá ABC")
                    Text("Đánh giá ABC")
                    Text("Đánh giá ABC")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRating(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        var rating: String by remember{ mutableStateOf("") }
        Card (modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth(fraction = 0.9f)
            .fillMaxHeight(fraction = 0.4f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround){
                Row {
                    Text("Đánh giá: ")
                    StarRating(score = 0.0)
                }
                OutlinedTextField(
                    value = rating,
                    onValueChange = { rating = it },
                    label = { Text("Đánh giá của bạn") }
                )
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = { /*TODO*/ }) {
                        Text("Gửi")
                    }
                    Button(onClick = { onDismissRequest() }) {
                        Text("Hủy")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DocLNTheme {
        ExpandRating()
    }
}