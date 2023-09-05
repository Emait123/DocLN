package com.example.docln

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

data class DataModel (
    val category: String,
    val imageUrl: String,
    val name: String,
    val desc: String) {
}

data class Novel (
    val id_truyen: Int,
    val ten_truyen: String,
    val coverImg: String,
)

data class Chapter(
    val id_chuong: Int,
    val STT: Int,
    val ten_chuong: String
)

data class NovelDetail (
    val id_truyen: Int = 0,
    var ten_truyen: String = "de",
    val coverImg: String = "",
    val dsChuong: List<Chapter>
)

data class ChapterContent(
    val id_chuong: Int,
    val ten_chuong: String,
    val noidung: String
)
