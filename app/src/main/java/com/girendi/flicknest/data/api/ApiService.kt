package com.girendi.flicknest.data.api

import com.girendi.flicknest.data.models.Movie
import com.girendi.flicknest.data.response.ListGenresResponse
import com.girendi.flicknest.data.response.ListMovieResponse
import com.girendi.flicknest.data.response.ListReviewResponse
import com.girendi.flicknest.data.response.ListVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getList(): Call<ListGenresResponse>

    @GET("discover/movie")
    fun discoverMoviesByGenre(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int
    ): Call<ListMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Call<Movie>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Call<ListReviewResponse>

    @GET("movie/{movieId}/videos")
    fun fetchMovieTrailers(
        @Path("movieId") movieId: Int
    ): Call<ListVideoResponse>
}