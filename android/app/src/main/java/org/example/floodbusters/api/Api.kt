package org.example.floodbusters.api

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

data class Risk(val safe_spot: Boolean, val advise: String)
data class SaveSpot(val latitude: Double, val longitude: Double)
data class Status(val last_status: String, val status: String)

interface ApiService {
    @GET("getRisk")
    fun getRisk(): Risk

    @GET("getSaveSpot")
    fun getSaveSpot(@Path("lat") latitude: Double, @Path("long") longitude: Double): SaveSpot

    @GET("getStatus")
    fun getStatus(@Path("name") name: String): Status
}

val retrofit = Retrofit.Builder().baseUrl("https://floodbustersbe2.azurewebsites.net").build()
val apiService = retrofit.create(ApiService::class.java)
