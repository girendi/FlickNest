package com.girendi.flicknest.presentation.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.core.domain.model.Genre
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.data.UiState
import com.girendi.flicknest.core.domain.usecase.FetchGenreUseCase
import com.girendi.flicknest.core.utils.DataMapperGenre
import kotlinx.coroutines.launch

class GenreViewModel(private val fetchGenreUseCase: FetchGenreUseCase) : ViewModel() {
    private val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenre

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = fetchGenreUseCase.fetchGenreMovie()) {
                is Result.Success -> {
                    _listGenre.value = DataMapperGenre.mapResponsesToDomain(result.data.listGenre)
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
}