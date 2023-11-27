package com.example.docln.plugins

import com.example.docln.ChapterContent
import com.example.docln.LoginResponse
import com.example.docln.Novel
import com.example.docln.NovelDetail
import com.example.docln.ReviewContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitAPI {

    @GET("listtruyen")
    suspend fun getNovelList(): List<Novel>

    @GET("gettruyen/{truyen_id}")
    suspend fun getNovelDetail(
        @Path(value="truyen_id", encoded = true) id: Int): NovelDetail

    @GET("gettruyen/{truyen_id}/{userID}")
    suspend fun getNovelDetail(
        @Path(value="truyen_id", encoded = true) id: Int,
        @Path(value="userID", encoded = true) userID: Int): NovelDetail

    @GET("chapter")
    suspend fun getChapterContent(
        @Query("truyen_id") truyenID : Int,
        @Query("chap_id") chapID : Int): ChapterContent

    @GET("login")
    suspend fun loginUser(
        @Query("UserId") userID : String ,
        @Query("Password") password : String): LoginResponse

    @GET("register")
    suspend fun registerUser(
        @Query("displayName") displayName: String,
        @Query("loginName") loginName: String,
        @Query("password") password: String): LoginResponse

    @GET("follow")
    suspend fun followNovel(
        @Query("novelID") novelID : Int,
        @Query("userID") userID : Int): Boolean

    @GET("review")
    suspend fun sendReview(
        @Query("novelID") novelID : Int,
        @Query("userID") userID : Int,
        @Query("rating") rating : Float,
        @Query("content") content : String): List<ReviewContent>

    @GET("search")
    suspend fun searchNovel( @Query("name") name: String ): List<Novel>

    @GET("followList")
    suspend fun getFollowList( @Query("userID") userID : Int): List<Novel>
    @GET("change-password")
    suspend fun changePassword(
        @Query("loginName") loginName: String,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String): LoginResponse


    companion object {
        var retrofitAPI: RetrofitAPI? = null
        fun getInstance(): RetrofitAPI {
            if (retrofitAPI == null) {
                retrofitAPI = Retrofit.Builder()
                    .baseUrl("https://mialotus.top/android/")
                    .addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitAPI::class.java)
            }
            return retrofitAPI!!
        }
    }
}