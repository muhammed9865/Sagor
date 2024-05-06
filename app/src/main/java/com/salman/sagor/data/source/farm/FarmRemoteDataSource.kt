package com.salman.sagor.data.source.farm

import android.util.Log
import com.salman.sagor.data.source.farm.model.TankDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class FarmRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient,
) {

    companion object {
        private const val PATH_TANKS = "https://sagor.onrender.com/api/v1/tanks/"
        private const val TAG = "FarmRemoteDataSource"
    }

    suspend fun getTankById(id: Int): Result<TankDTO> = runCatching {
        Log.d(TAG, "getTankById: Getting Tank by id: $id")
        val result = httpClient.get("$PATH_TANKS$id")
            .body<TankDTO>()

        result
    }

    suspend fun getAllTanks(): Result<List<TankDTO>> = runCatching {
        Log.d(TAG, "getAllTanks: Getting all tanks")
        val result = httpClient.get(PATH_TANKS)
            .body<List<TankDTO>>()

        result
    }

}