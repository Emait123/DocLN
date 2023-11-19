package com.example.docln.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.docln.Novel
import com.example.docln.R
import com.example.docln.Routes
import com.example.docln.viewmodels.LoginViewModel
import com.example.docln.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel = viewModel<MainViewModel>()
    viewModel.checkLoginState()
    val loginState = viewModel.isUserLoggedIn
    val userName = viewModel.userName

    val menuList = listOf(Routes.Home, Routes.Search)
    val userList = listOf(Routes.Login, Routes.Register)
    val selectedItem = remember { mutableStateOf(menuList[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column (modifier = Modifier.padding(12.dp)) {
                    Text("Xin chào $userName")
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    menuList.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                            label = { Text(item.name) },
                            selected = (item == selectedItem.value),
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                                navController.navigate(item.route)
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                    if (loginState) {
                        NavigationDrawerItem(
                            icon = { Icon(imageVector = Routes.Follow.icon, contentDescription = Routes.Follow.name) },
                            label = { Text(Routes.Follow.name) },
                            selected = (Routes.Follow == selectedItem.value),
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = Routes.Follow
                                navController.navigate(Routes.Follow.route)
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    if (!loginState) {
                        userList.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                                label = { Text(item.name) },
                                selected = (item == selectedItem.value),
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                    navController.navigate(item.route)
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Đọc LN") },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                        navigationIcon = { 
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            if (!loginState) {
                                IconButton(onClick = { navController.navigate(Routes.Login.route) }) {
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Trang đăng nhập"
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    viewModel.logOut()
                                    viewModel.checkLoginState() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "Đăng xuất"
                                    )
                                }
                            }
                        }
                ) },
                content = { paddingValues -> HomeScreenContent(navController,Modifier.padding(paddingValues)) }
            )
        }
    )
}

@Composable
fun HomeScreenContent (navController: NavController, modifier: Modifier) {
    val viewModel = viewModel<MainViewModel>()
    viewModel.getNovelList()
    val novelList = viewModel.novelListResponse
    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(modifier = modifier.padding(5.dp)) {
            Text(text = "CHƯƠNG",
                color = Color.White,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(5.dp))
            Text(text = "MỚI", modifier = Modifier.padding(top = 5.dp, start = 5.dp))
        }

        AnimatedVisibility(visible = novelList.isEmpty()) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }
        LazyRow () {
            itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
                    index, item -> NovelItem(navController, novel = item)
            }
        }

        Row(modifier = modifier.padding(start = 5.dp)) {
            Text(text = "SÁNG TÁC",
                color = Color.White,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(5.dp))
            Text(text = "MỚI", modifier = Modifier.padding(top = 5.dp))
        }
        AnimatedVisibility(visible = novelList.isEmpty(), Modifier.align(Alignment.CenterHorizontally)) {
            CircularProgressIndicator()
        }
        LazyRow () {
            itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
                    index, item -> NovelItem(navController, novel = item)
            }
        }

        Row(modifier = modifier.padding(start = 5.dp)) {
            Text(text = "TRUYỆN",
                color = Color.White,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(5.dp))
            Text(text = "TOP", modifier = Modifier.padding(top = 5.dp))
        }
        AnimatedVisibility(visible = novelList.isEmpty(), Modifier.align(Alignment.CenterHorizontally)) {
            CircularProgressIndicator()
        }
        LazyRow () {
            itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
                    index, item -> NovelItem(navController, novel = item)
            }
        }
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