package com.salman.sagor.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 6/27/2024.
 */
data class PumpActivityItem(
    val id: Int,
    val date: LocalDateTime,
    val status: PumpActivityStatus
)

enum class PumpActivityStatus {
    SUCCESS, PENDING
}