package com.shubhamtripz.iplbubble.presentation.matches.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.iplbubble.data.repository.StatsRepository
import com.shubhamtripz.iplbubble.domain.models.PlayerStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsViewModel(private val repository: StatsRepository) : ViewModel() {
    private val _stats = MutableStateFlow<List<PlayerStats>?>(null)
    val stats = _stats.asStateFlow()

    init {
        fetchStats()
    }

    private fun fetchStats() {
        viewModelScope.launch {
            _stats.value = repository.getPlayerStats()
        }
    }
}