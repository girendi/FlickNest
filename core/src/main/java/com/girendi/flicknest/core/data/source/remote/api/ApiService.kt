package com.girendi.flicknest.core.data.source.remote.api

import com.girendi.flicknest.core.data.source.remote.response.ListGenresResponse
import com.girendi.flicknest.core.data.source.remote.response.ListMovieResponse
import com.girendi.flicknest.core.data.source.remote.response.ListReviewResponse
import com.girendi.flicknest.core.data.source.remote.response.ListVideoResponse
import com.girendi.flicknest.core.data.source.remote.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun fetchMovieGenres(): Response<ListGenresResponse>

    @GET("discover/movie")
    suspend fun fetchMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String
    ): Response<ListMovieResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun fetchMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<ListReviewResponse>

    @GET("movie/{movieId}/videos")
    suspend fun fetchMovieVideos(
        @Path("movieId") movieId: Int
    ): Response<ListVideoResponse>

    @GET("movie/popular")
    suspend fun fetchPopularMovie(
        @Query("page") page: Int
    ): Response<ListMovieResponse>

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovie(
        @Query("page") page: Int
    ): Response<ListMovieResponse>
}