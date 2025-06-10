package com.n1n3b1t.thetest.main.domain

import com.n1n3b1t.thetest.network.TestAPI

class PointRepository(private val testAPI: TestAPI) {
    suspend fun getPoints(count: Int) = testAPI.getTestPoints(count).points
}