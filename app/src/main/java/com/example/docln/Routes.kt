package com.example.docln

sealed class Routes (val route: String) {
    object Home : Routes("home")
    object NovelDetail : Routes("novelDetail")
    object Chapter : Routes("chapter")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}