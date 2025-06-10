package com.n1n3b1t.thetest.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TestAPI {
    @GET("test/points")
    suspend fun getTestPoints(@Query("count") count: Int): PointResponse
}
