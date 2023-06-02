package com.example.monetanbaplayersapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetanbaplayersapp.data.models.Player
import com.example.monetanbaplayersapp.data.models.Team
import com.example.monetanbaplayersapp.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(private val repository: PlayerRepository): ViewModel() {
    private var currentPage = 1
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players
    private var totalPages = Int.MAX_VALUE

    private val _networkError = MutableStateFlow(false)
    val networkError: StateFlow<Boolean> = _networkError


    init {
        fetchAllPlayers()
    }


    fun fetchAllPlayers() {
        viewModelScope.launch {
            try {
                if (currentPage <= totalPages) {
                    val response = repository.getAllPlayers(currentPage)
                    if (response.isSuccessful && response.body() != null) {
                        _players.value = _players.value + response.body()!!.data
                        totalPages = response.body()!!.meta.total_pages
                        currentPage++
                    }
                }
            } catch (e: Exception) {
                _networkError.value = true
            }
        }
    }

    fun getPlayerById(id: Int?): Player? {
        return players.value.find { player -> player.id == id }
    }

    fun getTeamById(id: Int?): Team? {
        return players.value.map { it.team }.find { team -> team.id == id }
    }
}