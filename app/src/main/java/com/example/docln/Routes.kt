package com.example.docln

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val name: String, val icon: ImageVector, val route: String) {

    object Home : Routes(name = "Trang chủ", icon = Icons.Filled.Home, route = "home")
    object Ranking : Routes(name = "Bảng xếp hạng", icon = Icons.Filled.Star, route = "ranking")
    object Search : Routes(name = "Tìm kiếm", icon = Icons.Filled.Search, route = "search")
    object NovelDetail : Routes(name = "Thông tin chuyện", icon = Icons.Filled.Home, route = "novelDetail")
    object Chapter : Routes(name = "Nội dung chương", icon = Icons.Filled.Home, route = "chapter")
    object Login : Routes(name = "Đăng nhập", icon = Icons.Filled.AccountCircle, route = "login")
    object Register : Routes(name = "Đăng ký", icon = Icons.Filled.Info, route = "register")
    object Follow : Routes(name = "Truyện theo dõi", icon = Icons.Filled.Favorite, route = "follow")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}