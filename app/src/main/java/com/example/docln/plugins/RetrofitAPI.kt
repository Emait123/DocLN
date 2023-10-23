package com.example.docln.plugins

import com.example.docln.ChapterContent
import com.example.docln.LoginResponse
import com.example.docln.Novel
import com.example.docln.NovelDetail
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
    suspend fun getNovelDetail(@Path(value="truyen_id", encoded = true) id:Int): NovelDetail

    @GET("chapter/{chap_id}")
    suspend fun getChapterContent(@Path(value="chap_id", encoded = true) id:Int): ChapterContent

    @GET("login")
    suspend fun loginUser(@Query("UserId") userID : String , @Query("Password") password : String): LoginResponse
//    suspend fun loginUser(@Body login: LoginResponse): String


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