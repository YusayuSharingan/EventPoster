package ru.ituli.eventposter.webApi

import retrofit2.http.Query
import retrofit2.http.GET
import java.time.Instant


interface KudaGoApiService {
    @GET("public-api/v1.4/events/")
    suspend fun getEvents(
        @Query("lang") lang: String = "ru",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 40,
        @Query("fields") fields: String = "id,dates,title,place,description,price,images,site_url",
        @Query("expand") expand: String = "place",
        @Query("actual_since") actualSince: String = Instant.now().toString()
    ): EventResponse
}