package com.a5bar.network.remote

import com.a5bar.common.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsClient {

    companion object
    {
        private val retrofit by lazy {

            val log = HttpLoggingInterceptor()
            log.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(log)
                .build()

            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val getApiNew by lazy {
            retrofit.create(NewsService::class.java)
        }
    }
}