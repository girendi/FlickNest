package com.girendi.flicknest.core.data.repository

import com.girendi.flicknest.core.data.api.ApiService
import com.girendi.flicknest.core.data.response.ListGenresResponse
import com.girendi.flicknest.core.domain.model.Genre
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepositoryImpl(private val apiService: ApiService) : BaseRepository(), GenreRepository {
    override suspend fun fetchGenreMovie(): Result<ListGenresResponse> =
        apiRequest { apiService.fetchMovieGenres() }
}