package com.girendi.flicknest.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.UiState
import com.girendi.flicknest.domain.usecase.FetchMovieByGenreUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ListMovieViewModel(private val fetchMovieByGenreUseCase: FetchMovieByGenreUseCase) : ViewModel() {

    private val _listMovie = MutableLiveData<List<Movie>>()
    val listMovie: LiveData<List<Movie>> = _listMovie

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private var currentPage = 1
    private var isLastPage = false

    fun fetchMovieByGenre(genreId: String) {
        if (isLastPage) {
            _uiState.postValue(UiState.Success)
            return
        }

        viewModelScope.launch {
            when(val result = fetchMovieByGenreUseCase(currentPage, genreId)) {
                is Result.Success -> {
                    val movies = _listMovie.value.orEmpty() + result.data
                    _listMovie.postValue(movies)
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

    fun getPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }

}