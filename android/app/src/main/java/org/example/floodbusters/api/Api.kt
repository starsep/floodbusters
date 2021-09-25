package org.example.floodbusters.api

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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
    fun getRisk(): Response<Risk>

    @GET("getSaveSpot")
    fun getSaveSpot(@Path("lat") latitude: Double, @Path("long") longitude: Double): Response<SaveSpot>

    @GET("getStatus")
    fun getStatus(@Path("name") name: String): Response<Status>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://floodbustersbe2.azurewebsites.net")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
val apiService = retrofit.create(ApiService::class.java)
