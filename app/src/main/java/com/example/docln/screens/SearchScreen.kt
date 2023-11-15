package com.example.docln.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.docln.Novel
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = viewModel<SearchViewModel>()
    val data = viewModel.novelListResponse

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm kiếm") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            ) },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val (searchKeyword, setSearchKeyword) = remember { mutableStateOf(TextFieldValue()) }
                    TextField(
                        value = searchKeyword,
                        onValueChange = { setSearchKeyword(it) },
                        label = { Text("Nhập từ khóa tìm kiếm") },
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
                            val keyword = searchKeyword.text
                            // Xử lý tìm kiếm với từ khóa keyword ở đây
                            // Có thể chuyển keyword cho Activity hoặc ViewModel để xử lý tiếp
                            viewModel.searchNovel(keyword)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Tìm Kiếm")
                    }
                    Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))
                }
                if (data.isNotEmpty()) {
                    itemsIndexed(items = data, key = {index, novel -> novel.id_truyen}) {
                            index, item -> SearchItem(navController, novel = item)
                    }
                }
            } }
    )
}

@Composable
fun SearchItem(navController: NavController, novel: Novel) {
    Row(
        modifier = Modifier.clickable {
            navController.navigate(Routes.NovelDetail.withArgs(novel.id_truyen.toString()))
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .size(width = 90.dp, height = 250.dp)
                .weight(1f)
                .padding(start = 20.dp, bottom = 20.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(novel.coverImg)
                .memoryCacheKey(novel.coverImg)
                .diskCacheKey(novel.coverImg)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .error(R.drawable.nocover)
                .crossfade(true)
                .build(),
            contentDescription = "Novel Cover Image",
            error = painterResource(id = R.drawable.nocover),
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = novel.ten_truyen,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
        )
    }
}
