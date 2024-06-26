package com.salman.sagor.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
data class User(
    val id: Int,
    val token: String,
    val name: String,
    val phoneNumber: String,
)
