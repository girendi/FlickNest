package com.girendi.flicknest.presentation.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.girendi.flicknest.data.models.Movie
import com.girendi.flicknest.data.api.ApiConfig
import com.girendi.flicknest.data.response.ListMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class ListMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val _listMovie = MutableLiveData<List<Movie>>()
    val listMovie: LiveData<List<Movie>> = _listMovie

    private var page = 1

    fun getListMovie(genreId: String) {
        ApiConfig.provideApiService().discoverMoviesByGenre(genreId, page).enqueue(
            object : Callback<ListMovieResponse> {
                override fun onResponse(
                    call: Call<ListMovieResponse>,
                    response: Response<ListMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val currentList = _listMovie.value ?: emptyList()
                        val movies = response.body()?.listMovie ?: listOf()
                        _listMovie.postValue(currentList + movies)
                    }
                    page++
                }

                override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            }
        )
    }

    fun resetGetListMovie(genreId: String) {
        page = 1
        _listMovie.value = emptyList()
        getListMovie(genreId)
    }

    fun getPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }

}