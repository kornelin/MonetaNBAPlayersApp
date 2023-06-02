package com.example.monetanbaplayersapp.data.repository

import com.example.monetanbaplayersapp.data.api.ApiService
import com.example.monetanbaplayersapp.data.models.PlayersResponse
import retrofit2.Response
import javax.inject.Inject


class PlayerRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllPlayers(page: Int): Response<PlayersResponse> {
        return apiService.getAllPlayers(page)
    }
}
