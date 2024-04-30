package com.salman.sagor.data.repository

import com.salman.sagor.data.source.farm.FarmRemoteDataSource
import com.salman.sagor.domain.model.Tank
import javax.inject.Inject

class FarmRepository @Inject constructor(
    private val farmRemoteDataSource: FarmRemoteDataSource
) {

    suspend fun getAllPools(): List<Tank> {
        return emptyList()
    }

    suspend fun getPoolDetails(id: Int): Result<Tank> {
        return Result.failure(Exception())
    }
}