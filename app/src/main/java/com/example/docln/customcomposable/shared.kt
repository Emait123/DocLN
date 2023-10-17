package com.example.docln.customcomposable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.docln.Routes
import com.example.docln.screens.HomeScreenContent
import kotlinx.coroutines.launch


//Top app bar dùng chung cho các màn hình. Chưa xong
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController : NavController,
    title : String,
    content : @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuList = listOf(Routes.Home, Routes.Ranking, Routes.Search)
    val userList = listOf(Routes.Login, Routes.Register)
    val selectedItem = remember { mutableStateOf(menuList[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(text = "Menu")
                menuList.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                        label = { Text(item.name) },
                        selected = (item == selectedItem.value),
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(10.dp))
                Text(text = "User")
                userList.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                        label = { Text(item.name) },
                        selected = (item == selectedItem.value),
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        gesturesEnabled = true,
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(title) },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu điều hướng"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Routes.Login.route) }) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Trang đăng nhập"
                                )
                            }
                        }
                    ) },
                content = content
//                content = { paddingValues -> HomeScreenContent(navController, Modifier.padding(paddingValues)) }
            )
        }
    )
}