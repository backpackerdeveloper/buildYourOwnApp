package com.shubhamtripz.iplbubble.presentation.matches.pointstable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.iplbubble.domain.models.PointsTable
import com.shubhamtripz.iplbubble.domain.usecases.GetPointsTableUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PointsTableViewModel(private val useCase: GetPointsTableUseCase) : ViewModel() {

    private val _pointsTable = MutableStateFlow<List<PointsTable>>(emptyList())
    val pointsTable: StateFlow<List<PointsTable>> = _pointsTable

    init {
        fetchPointsTable()
    }

    private fun fetchPointsTable() {
        viewModelScope.launch {
            useCase.execute().collectLatest { data ->
                _pointsTable.value = data
            }
        }
    }
}