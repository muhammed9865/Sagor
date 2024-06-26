package com.salman.sagor.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/19/2024.
 */
data class Tank(
    val id: Int,
    val name: String,
    val packages: List<Package>,
)
