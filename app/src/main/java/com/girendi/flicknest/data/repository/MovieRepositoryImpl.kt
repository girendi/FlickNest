package com.girendi.flicknest.data.repository

import com.girendi.flicknest.data.api.ApiService
import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Review
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(private val api: ApiService): MovieRepository {
    override suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchMoviesByGenre(page, genreId)
                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body()!!.listMovie
                    Result.Success(movies)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchMovieDetail(movieId: Int): Result<Movie> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchMovieDetail(movieId)
                if (response.isSuccessful && response.body() != null) {
                    val movie = response.body()!!
                    Result.Success(movie)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchMovieReviews(page: Int, movieId: Int): Result<List<Review>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchMovieReviews(movieId, page)
                if (response.isSuccessful && response.body() != null) {
                    val reviews = response.body()!!.listReview
                    Result.Success(reviews)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchMovieVideos(movieId: Int): Result<List<Video>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchMovieVideos(movieId)
                if (response.isSuccessful && response.body() != null) {
                    val videos = response.body()!!.listVideo
                    Result.Success(videos)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchPopularMovies(page: Int): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchPopularMovie(page)
                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body()!!.listMovie
                    Result.Success(movies)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTrendingMovies(page: Int): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchTrendingMovie(page)
                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body()!!.listMovie
                    Result.Success(movies)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchMostTrendingMovies(page: Int): Result<Movie> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchTrendingMovie(page)
                if (response.isSuccessful && response.body() != null) {
                    val movie = response.body()!!.listMovie.firstOrNull()!!
                    Result.Success(movie)
                } else {
                    Result.Error(Exception("Failed to fetch genres: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}