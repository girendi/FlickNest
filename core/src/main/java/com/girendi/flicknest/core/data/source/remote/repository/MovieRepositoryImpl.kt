package com.girendi.flicknest.core.data.source.remote.repository

import com.girendi.flicknest.core.data.source.remote.api.ApiService
import com.girendi.flicknest.core.data.source.remote.response.ListMovieResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.data.source.local.LocalDataSource
import com.girendi.flicknest.core.data.source.remote.response.MovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.repository.MovieRepository
import com.girendi.flicknest.core.utils.DataMapperMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource
): BaseRepository(), MovieRepository {
    override suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse> =
        apiRequest { apiService.fetchMoviesByGenre(page, genreId) }
    override suspend fun fetchMovieDetail(movieId: Int): Result<MovieResponse> =
        apiRequest { apiService.fetchMovieDetail(movieId) }
    override suspend fun fetchPopularMovies(page: Int): Result<ListMovieResponse> =
        apiRequest { apiService.fetchPopularMovie(page) }
    override suspend fun fetchTrendingMovies(page: Int): Result<ListMovieResponse> =
        apiRequest { apiService.fetchTrendingMovie(page) }
    override suspend fun insertFavoriteMovie(movie: Movie) =
        localDataSource.insertMovie(
            DataMapperMovie.mapDomainToEntity(movie)
        )
    override fun getFavoriteMovieById(movieId: Int): Flow<Movie?> =
        localDataSource.getFavoriteMovieById(movieId).map {
            it?.let { it1 -> DataMapperMovie.mapEntityToDomain(it1) }
        }
    override suspend fun deleteFavoriteMovie(movie: Movie) =
        localDataSource.deleteMovie(
            DataMapperMovie.mapDomainToEntity(movie)
        )
}