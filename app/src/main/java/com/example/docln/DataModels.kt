package com.example.docln

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import com.google.gson.annotations.SerializedName




data class ReaderSetting (
    val backgroundColor: Color,
    val fontColor: Color,
    val fontSize: Int,
    val fontStyle: SystemFontFamily,
    val textAlign: TextAlign
) {
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
    val tomtat: String,
    val tacgia: String,
    val minhhoa: String,
    val tag: String,
    val trangthai: String,
    val follow: Boolean,
    val review: List<ReviewContent>,
    val dsChuong: List<Chapter>
)

data class ChapterContent(
    val id_truyen: Int,
    val id_chuong: Int,
    val ten_chuong: String,
    val noidung: String,
    val STT: Int,
    val dsChuong: List<Chapter>
)

data class login(
    val account_id: Int,
    val password: String,
    val username: String,
)

data class ReviewContent(
    val displayName: String,
    val rating: Float,
    val content: String
)

data class LoginResponse(
    @field:SerializedName("UserId") var userId: Int,
    @field:SerializedName("Password") var Password: String
) {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("displayName")
    var displayName: String = ""

    @SerializedName("ResponseMessage")
    var responseMessage: String? = null

}