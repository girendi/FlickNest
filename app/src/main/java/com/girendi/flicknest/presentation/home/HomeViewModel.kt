package com.girendi.flicknest.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.model.Video
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.data.UiState
import com.girendi.flicknest.core.domain.usecase.FetchMovieUseCase
import com.girendi.flicknest.core.utils.DataMapperMovie
import com.girendi.flicknest.core.utils.DataMapperVideo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel(private val fetchMovieUseCase: FetchMovieUseCase): ViewModel() {
    private val _mostTrending = MutableLiveData<Movie>()
    val mostTrending: LiveData<Movie> = _mostTrending

    private val _trending = MutableLiveData<List<Movie>>()
    val trending: LiveData<List<Movie>> = _trending

    private val _resultMovie = MutableLiveData<List<Movie>>()
    val resultMovie: LiveData<List<Movie>> = _resultMovie

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _video = MutableLiveData<Video>()
    val video: LiveData<Video> = _video

    private var currentPage = 1

    init {
        fetchTrendingMovie()
        fetchPopularMovies()
    }

    private fun fetchTrendingMovie() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = fetchMovieUseCase.fetchTrendingMovies(currentPage)) {
                is Result.Success -> {
                    val movie = DataMapperMovie.mapResponsesToDomain(result.data.listMovie)
                    _trending.value = movie
                    _mostTrending.value = movie.firstOrNull()!!
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

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = fetchMovieUseCase.fetchPopularMovies(currentPage)) {
                is Result.Success -> {
                    val movie = DataMapperMovie.mapResponsesToDomain(result.data.listMovie)
                    _resultMovie.postValue(movie)
                    _uiState.postValue(UiState.Success)
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
            _uiState.value = UiState.Loading
            when(val result = fetchMovieUseCase.fetchVideoByMovie(movieId)) {
                is Result.Success -> {
                    val videos = DataMapperVideo.mapResponsesToDomain(result.data.listVideo)
                    val trailers = videos.filter {
                        it.type == "Trailer"
                    }
                    if (trailers.isNotEmpty()) {
                        _video.value = trailers[0]
                    }
                    _uiState.value = UiState.Success
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

    fun getBackdropPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }
}