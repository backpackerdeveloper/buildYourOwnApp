package com.shubhamtripz.iplbubble.presentation.matches.iplfixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.iplbubble.domain.models.IplFixture
import com.shubhamtripz.iplbubble.domain.usecases.GetIplFixturesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IplFixturesViewModel(private val getIplFixturesUseCase: GetIplFixturesUseCase) : ViewModel() {
    private val _fixtures = MutableStateFlow<List<IplFixture>>(emptyList())
    val fixtures: StateFlow<List<IplFixture>> get() = _fixtures

    init {
        fetchFixtures()
    }

    private fun fetchFixtures() {
        viewModelScope.launch {
            getIplFixturesUseCase.execute()
                .catch { _fixtures.value = emptyList() }
                .collectLatest { _fixtures.value = it }
        }
    }
}