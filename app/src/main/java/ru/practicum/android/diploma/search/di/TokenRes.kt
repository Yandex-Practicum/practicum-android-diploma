package ru.practicum.android.diploma.search.di

data class TokenRes(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)