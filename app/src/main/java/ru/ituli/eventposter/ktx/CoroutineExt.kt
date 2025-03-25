package ru.ituli.eventposter.ktx

import java.util.concurrent.CancellationException

suspend fun <T> runCatchingNonCancellation(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (e: Exception) {
        Result.failure(e)
    }
}