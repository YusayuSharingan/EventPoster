package ru.ituli.eventposter.webApi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class KudaGoApiClient {
        private val url = "https://kudago.com"

        private val json = Json {
                ignoreUnknownKeys = true
        }

        private val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        private val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()

        val apiService: KudaGoApiService = retrofit.create(KudaGoApiService::class.java)
}