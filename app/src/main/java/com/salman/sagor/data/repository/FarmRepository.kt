package com.salman.sagor.data.repository

import com.salman.sagor.data.mapper.toDomainTank
import com.salman.sagor.data.source.farm.FarmRemoteDataSource
import com.salman.sagor.domain.model.Tank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject

class FarmRepository @Inject constructor(
    private val farmRemoteDataSource: FarmRemoteDataSource
) {

    companion object {
        // every 10 seconds, reload data
        private const val RELOAD_NEW_DATA_DELAY = 10000L
    }

    fun getAllTanks(): Flow<List<Tank>> = flow {
        while (currentCoroutineContext().isActive) {
            val remoteTanks = farmRemoteDataSource.getAllTanks()
            if (remoteTanks.isSuccess) {
                emit(remoteTanks.getOrThrow().map { it.toDomainTank() })
            } else
                emit(emptyList())

            delay(RELOAD_NEW_DATA_DELAY)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTankDetails(id: Int): Flow<Tank?> = flow {
        val currentContext = currentCoroutineContext()
        while (currentContext.isActive) {
            val remoteTank = farmRemoteDataSource.getTankById(id)
            if (remoteTank.isSuccess) {
                emit(remoteTank.getOrThrow().toDomainTank())
                delay(RELOAD_NEW_DATA_DELAY)
            } else {
                emit(null)
                currentContext.cancel(null)
            }
        }
    }.flowOn(Dispatchers.IO)
}