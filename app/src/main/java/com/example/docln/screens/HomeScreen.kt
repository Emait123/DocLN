package com.example.docln.screens

import android.graphics.Paint.Style
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.Novel
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar() },
    ) { paddingValues -> HomeScreenContent(navController,Modifier.padding(paddingValues),)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Đọc LN") },
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ModalNavigationDrawerSample(navController: NavController, modifier: Modifier, drawerState: DrawerState) {
////    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    // icons to mimic drawer destinations
//    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
//    val selectedItem = remember { mutableStateOf(items[0]) }
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Spacer(Modifier.height(12.dp))
//                items.forEach { item ->
//                    NavigationDrawerItem(
//                        icon = { Icon(item, contentDescription = null) },
//                        label = { Text(item.name) },
//                        selected = item == selectedItem.value,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            selectedItem.value = item
//                        },
//                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
//            }
//        },
//        content = {
//            HomeScreenContent(navController, modifier)
//        },
//        gesturesEnabled = true
//    )
//}

@Composable
fun HomeScreenContent (navController: NavController, modifier: Modifier) {
    val viewModel = viewModel<MainViewModel>()
    viewModel.getNovelList()
    val novelList = viewModel.novelListResponse
    Column {
        Text(text = "Chương mới", modifier = modifier)
        LazyRow () {
            itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
                    index, item -> NovelItem(navController, novel = item)
            }
        }
        Text("Sáng tác mới")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovelItem (navController: NavController, novel: Novel) {
    ElevatedCard (
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 180.dp, height = 300.dp)
            .padding(5.dp),
        onClick = { navController.navigate(Routes.NovelDetail.withArgs(novel.id_truyen.toString())) }
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.nocover)
                    .crossfade(true)
                    .build(),
                contentDescription = "Novel Cover Image",
                error = painterResource(id = R.drawable.nocover),
                contentScale = ContentScale.FillBounds,
//                    .clip(RoundedCornerShape(10.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
            )
            Text(
                text = novel.ten_truyen,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}