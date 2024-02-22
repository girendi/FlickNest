package com.girendi.flicknest.data.repository

import com.girendi.flicknest.data.api.ApiService
import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepositoryImpl(private val api: ApiService) : GenreRepository {
    override suspend fun fetchGenres(): Result<List<Genre>> = withContext(Dispatchers.IO) {
        try {
            val response = api.fetchMovieGenres()
            if (response.isSuccessful && response.body() != null) {
                val genres = response.body()!!.listGenre.map { networkGenre ->
                    Genre(networkGenre.id, networkGenre.name)
                }
                Result.Success(genres)
            } else {
                Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}