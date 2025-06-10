package com.n1n3b1t.thetest.main.domain.usecase

import com.n1n3b1t.thetest.core.UseCase
import com.n1n3b1t.thetest.main.domain.PointRepository
import com.n1n3b1t.thetest.network.Point

class GetPointsUseCase(private val pointRepository: PointRepository) : UseCase<Int, List<Point>> {

    override suspend fun execute(input: Int): List<Point> {
        val points = pointRepository.getPoints(input)
        if (points.size != input) {
            throw WrongPointsException("Expected $input points, but got ${points.size}")
        }
        return points.sortedBy { it.x }
    }
}

class WrongPointsException(
    message: String = "Wrong number of points received",
    cause: Throwable? = null
) : Exception(message, cause) {
    constructor(cause: Throwable) : this("Wrong number of points received", cause)
}