package com.example.cliwaves.fragments.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cliwaves.data.RemoteLocation
import com.example.cliwaves.network.repository.WeatherDataRepository
import kotlinx.coroutines.launch
import retrofit2.http.Query

class LocationViewModel(private val weatherDataRepository: WeatherDataRepository): ViewModel() {

    private val _searchResult = MutableLiveData<SearchResultDataState>()
    val searchResult: LiveData<SearchResultDataState> get() = _searchResult

    fun searchLocation(query: String) {
        viewModelScope.launch {
            emitSearchResultUiState(isLoading = true)
            val searchResult = weatherDataRepository.searchLocation(query)
            if (searchResult.isNullOrEmpty()) {
                emitSearchResultUiState(error = "Location not found!, please try again.")
            } else {
                emitSearchResultUiState(locations = searchResult)
            }
        }
    }

    private fun emitSearchResultUiState(
        isLoading: Boolean = false,
        locations: List<RemoteLocation>? = null,
        error: String? = null
    ) {
        val searchResultDataState = SearchResultDataState(isLoading, locations, error)
        _searchResult.value = searchResultDataState
    }

    data class SearchResultDataState(
        val isLoading: Boolean,
        val locations: List<RemoteLocation>?,
        val error: String?
    )

}