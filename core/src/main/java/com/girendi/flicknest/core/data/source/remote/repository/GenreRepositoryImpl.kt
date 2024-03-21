package com.girendi.flicknest.core.data.source.remote.repository

import com.girendi.flicknest.core.data.source.remote.api.ApiService
import com.girendi.flicknest.core.data.source.remote.response.ListGenresResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.domain.repository.GenreRepository

class GenreRepositoryImpl(private val apiService: ApiService) : BaseRepository(),
    GenreRepository {
    override suspend fun fetchGenreMovie(): Result<ListGenresResponse> =
        apiRequest { apiService.fetchMovieGenres() }
}