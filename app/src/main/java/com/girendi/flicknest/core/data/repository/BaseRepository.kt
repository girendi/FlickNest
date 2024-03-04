package com.girendi.flicknest.core.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.girendi.flicknest.core.domain.Result
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> apiRequest(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext Result.Success(it)
                    } ?: Result.Error(Exception("Data is null"))
                } else {
                    Result.Error(Exception("API call failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}