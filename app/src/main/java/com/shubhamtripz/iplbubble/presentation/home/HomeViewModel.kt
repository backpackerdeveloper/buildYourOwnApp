package com.shubhamtripz.iplbubble.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.iplbubble.data.repository.HomeRepository
import com.shubhamtripz.iplbubble.domain.models.HomeApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _isBubbleEnabled = MutableStateFlow(false)
    val isBubbleEnabled: StateFlow<Boolean> = _isBubbleEnabled

    private val _homeData = MutableStateFlow<List<HomeApiResponse>>(emptyList())
    val homeData: StateFlow<List<HomeApiResponse>> = _homeData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            repository.getHomeDataStream().collectLatest { cachedData ->
                if (cachedData.isNotEmpty()) {
                    _homeData.value = cachedData
                }
            }
        }
    }

    fun setBubbleEnabled(enabled: Boolean) {
        viewModelScope.launch {
            _isBubbleEnabled.value = enabled
        }
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // This will either fetch from API or return cached data based on connectivity
                val result = repository.fetchHomeData()
                if (result.isNotEmpty()) {
                    _homeData.value = result
                }
            } catch (e: Exception) {
                // later use
            } finally {
                _isLoading.value = false
            }
        }
    }
}