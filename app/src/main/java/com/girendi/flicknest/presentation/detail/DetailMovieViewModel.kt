package com.girendi.flicknest.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.girendi.flicknest.data.models.Movie
import com.girendi.flicknest.data.models.Review
import com.girendi.flicknest.data.api.ApiConfig
import com.girendi.flicknest.data.models.Video
import com.girendi.flicknest.data.response.ListReviewResponse
import com.girendi.flicknest.data.response.ListVideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _listReview = MutableLiveData<List<Review>>()
    val listReview: LiveData<List<Review>> = _listReview

    private val _listVideo = MutableLiveData<List<Video>>()
    val listVideo: LiveData<List<Video>> = _listVideo

    private var page = 1

    fun getDetailMovie(id: Int) {
        ApiConfig.provideApiService().getMovieDetails(id).enqueue(
            object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if (response.isSuccessful) {
                        _movie.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            }
        )
    }

    fun getListReview(movieId: Int) {
        ApiConfig.provideApiService().getMovieReviews(movieId, page).enqueue(
            object : Callback<ListReviewResponse> {
                override fun onResponse(
                    call: Call<ListReviewResponse>,
                    response: Response<ListReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        val currentList = _listReview.value ?: emptyList()
                        val reviews = response.body()?.listReview ?: listOf()
                        _listReview.postValue(currentList + reviews)
                        page++
                    }
                }

                override fun onFailure(call: Call<ListReviewResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            }
        )
    }

    fun fetchMovieTrailers(movieId: Int) {
        ApiConfig.provideApiService().fetchMovieTrailers(movieId).enqueue(
            object : Callback<ListVideoResponse> {
                override fun onResponse(call: Call<ListVideoResponse>, response: Response<ListVideoResponse>) {
                    if (response.isSuccessful) {
                        val trailers = response.body()?.listVideo?.filter {
                            it.type == "Trailer"
                        }
                        _listVideo.postValue(trailers!!)
                    }
                }

                override fun onFailure(call: Call<ListVideoResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            }
        )
    }

    fun resetGetListReview(movieId: Int) {
        page = 1
        _listReview.value = emptyList()
        getListReview(movieId)
    }

    fun getPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }

    fun reformatDateString(originalDateString: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = originalFormat.parse(originalDateString)
        val newFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)
        return newFormat.format(date ?: return "Invalid Date")
    }

}