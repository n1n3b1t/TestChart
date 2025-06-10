package com.n1n3b1t.thetest.core

interface UseCase<T, R> {
    suspend fun execute(input: T): R
}

suspend inline fun <T> UseCase<Unit, T>.execute(): T = execute(Unit)