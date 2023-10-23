package com.example.docln

import com.google.gson.annotations.SerializedName




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
    val tomtat: String,
    val dsChuong: List<Chapter>
)

data class ChapterContent(
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

data class LoginResponse(
    @field:SerializedName("UserId") var userId: String,
    @field:SerializedName("Password") var Password: String
) {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("displayName")
    var displayName: String? = null

    @SerializedName("ResponseMessage")
    var responseMessage: String? = null

}