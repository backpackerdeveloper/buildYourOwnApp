package com.shubhamtripz.iplbubble.presentation.home.livescore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.iplbubble.data.repository.MatchRepository
import com.shubhamtripz.iplbubble.domain.models.MatchData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(private val repository: MatchRepository) : ViewModel() {

    private val _matchData = MutableStateFlow<MatchData?>(null)
    val matchData: StateFlow<MatchData?> = _matchData

    fun fetchMatchDetails() {
        viewModelScope.launch {
            while (true) {
                repository.fetchLiveScore { data ->
                    _matchData.value = data
                }
                delay(8000) // delay used so we can update live score, we need to keep scraping and updating the web-view in background
            }
        }
    }
}
