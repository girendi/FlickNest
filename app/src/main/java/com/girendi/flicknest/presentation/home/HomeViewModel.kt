package com.girendi.flicknest.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.UiState
import com.girendi.flicknest.domain.usecase.FetchMovieUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel(private val fetchMovieUseCase: FetchMovieUseCase): ViewModel() {
    private val _mostTrending = MutableLiveData<Result<Movie>>()
    val mostTrending: LiveData<Result<Movie>> = _mostTrending

    private val _trending = MutableLiveData<Result<List<Movie>>>()
    val trending: LiveData<Result<List<Movie>>> = _trending

    private val _resultVideos = MutableLiveData<Result<List<Video>>>()
    val resultVideos: LiveData<Result<List<Video>>> = _resultVideos

    private val _resultMovie = MutableLiveData<List<Movie>>()
    val resultMovie: LiveData<List<Movie>> = _resultMovie

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private var currentPage = 1
    private var isLastPage = false

    init {
        fetchTrendingMovie()
        fetchMostTrendingMovie()
        fetchPopularMovies()
    }

    private fun fetchTrendingMovie() {
        viewModelScope.launch {
            _trending.value = Result.Loading
            _trending.value = fetchMovieUseCase.fetchTrendingMovies(1)
        }
    }

    private fun fetchMostTrendingMovie() {
        viewModelScope.launch {
            _mostTrending.value = Result.Loading
            _mostTrending.value = fetchMovieUseCase.fetchMostTrendingMovies(1)
        }
    }

    private fun fetchPopularMovies() {
        if (isLastPage) {
            _uiState.postValue(UiState.Success)
            return
        }
        viewModelScope.launch {
            when(val result = fetchMovieUseCase.fetchPopularMovies(1)) {
                is Result.Success -> {
                    val movies = _resultMovie.value.orEmpty() + result.data
                    _resultMovie.postValue(movies)
                    _uiState.postValue(UiState.Success)
                    isLastPage = result.data.isEmpty()
                    currentPage++
                }
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.exception.message ?: "An unknown error occurred")
                }
                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun fetchMovieVideos(movieId: Int) {
        viewModelScope.launch {
            _resultVideos.value = Result.Loading
            _resultVideos.value = fetchMovieUseCase.fetchMovieVideos(movieId)
        }
    }

    fun getBackdropPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }
}