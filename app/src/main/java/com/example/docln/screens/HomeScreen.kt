package com.example.docln.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.Novel
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.viewmodels.MainViewModel

@Composable
fun HomeScreen (navController: NavController) {
    val viewModel = viewModel<MainViewModel>()
    viewModel.getNovelList()
    val novelList = viewModel.novelListResponse
    LazyColumn {
        itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
                index, item -> NovelItem(navController, novel = item)
        }
    }
}

@Composable
fun NovelItem (navController: NavController, novel: Novel) {
//    val navController = rememberNavController()
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(150.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Surface() {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .clickable {
                        navController.navigate(Routes.NovelDetail.withArgs(novel.id_truyen.toString()))
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.nocover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Novel Cover Image",
                    error = painterResource(id = R.drawable.nocover),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))

                )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Text(
                        text = novel.ten_truyen,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopStart),

                        )
                    Text(
                        text = "Lorem ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterStart),
                    )
                }
            }
        }
    }
}