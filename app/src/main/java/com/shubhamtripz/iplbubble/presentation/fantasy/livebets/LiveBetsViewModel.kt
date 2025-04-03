package com.shubhamtripz.iplbubble.presentation.fantasy.livebets

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shubhamtripz.iplbubble.domain.models.Bet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Feature under Construction

class LiveBetsViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().getReference("live_bets")

    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets: StateFlow<List<Bet>> = _bets.asStateFlow()

    init {
        fetchBets()
    }

    private fun fetchBets() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val betsList = mutableListOf<Bet>()
                for (betSnapshot in snapshot.children) {
                    betSnapshot.getValue(Bet::class.java)?.let { betsList.add(it) }
                }
                _bets.value = betsList
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}