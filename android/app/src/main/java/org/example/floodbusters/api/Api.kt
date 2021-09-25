package org.example.floodbusters.api

import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Risk(
    @field:Json(name = "safe_spot") val isSpotSafe: Boolean,
    @field:Json(name = "advise") val advise: String,
)

data class SaveSpot(
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double,
)

data class Status(
    @field:Json(name = "last_status") val lastStatus: String,
    @field:Json(name = "status") val status: String,
)

interface ApiService {
    @GET("getRisk")
    suspend fun getRisk(@Query("lat") latitude: Double, @Query("long") longitude: Double): Risk

    @GET("getSaveSpot")
    suspend fun getSaveSpot(@Query("lat") latitude: Double, @Query("long") longitude: Double): SaveSpot

    @GET("getStatus")
    suspend fun getStatus(@Query("name") name: String): Status
}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://floodbustersbe2.azurewebsites.net")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
