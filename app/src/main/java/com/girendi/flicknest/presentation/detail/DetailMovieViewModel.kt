package com.girendi.flicknest.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.model.Review
import com.girendi.flicknest.core.domain.model.Video
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.UiState
import com.girendi.flicknest.core.domain.usecase.FetchDetailMovieUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailMovieViewModel(private val fetchDetailMovieUseCase: FetchDetailMovieUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _resultMovie = MutableLiveData<Result<Movie>>()
    val resultMovie: LiveData<Result<Movie>> = _resultMovie

    private val _listReview = MutableLiveData<List<Review>>()
    val listReview: LiveData<List<Review>> = _listReview

    private val _video = MutableLiveData<Video>()
    val video: LiveData<Video> = _video

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var currentPage = 1
    private var isLastPage = false

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _resultMovie.value = Result.Loading
            _resultMovie.value = fetchDetailMovieUseCase.fetchMovieDetail(movieId)
        }
    }
    fun fetchReviewByMovie(movieId: Int) {
        if (isLastPage) {
            _uiState.postValue(UiState.Success)
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = fetchDetailMovieUseCase.fetchReviewByMovie(currentPage, movieId)) {
                is Result.Success -> {
                    val reviews = _listReview.value.orEmpty() + result.data.listReview
                    isLastPage = result.data.listReview.isEmpty()
                    _listReview.postValue(reviews)
                    _uiState.postValue(UiState.Success)
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

    fun fetchVideoByMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = fetchDetailMovieUseCase.fetchVideoByMovie(movieId)) {
                is Result.Success -> {
                    val videos = result.data.listVideo
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