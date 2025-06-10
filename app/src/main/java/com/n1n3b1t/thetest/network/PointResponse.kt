package com.n1n3b1t.thetest.network

import kotlinx.serialization.Serializable

@Serializable
data class Point(
    val x: Float,
    val y: Float
)

@Serializable
data class PointResponse(
    val points: List<Point>
)