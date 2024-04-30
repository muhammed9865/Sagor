package com.salman.sagor.data.source.farm

import com.salman.sagor.data.source.farm.model.TankDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class FarmRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient,
) {

    companion object {
        private const val PATH_TANKS = "tanks/"
    }

    suspend fun getTankById(id: Int): Result<TankDTO> = runCatching {
        val result = httpClient.get("$PATH_TANKS$id")
            .body<TankDTO>()

        result
    }

    suspend fun getAllTanks(): Result<List<TankDTO>> = runCatching {
        val result = httpClient.get(PATH_TANKS)
            .body<List<TankDTO>>()

        result
    }

}