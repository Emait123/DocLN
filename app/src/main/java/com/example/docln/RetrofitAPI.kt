package com.example.docln

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitAPI {

    @GET("listtruyen/1")
    suspend fun getNovelList(): List<Novel>

    @GET("gettruyen/{truyen_id}")
    suspend fun getNovelDetail(@Path(value="truyen_id", encoded = true) id:Int): NovelDetail

    @GET("chapter/{chap_id}")
    suspend fun getChapterContent(@Path(value="chap_id", encoded = true) id:Int): ChapterContent


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