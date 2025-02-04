package ru.practicum.android.diploma.filter.data.dto.model

data class CountryDto(
    val id: String,
    val name: String,
    val countries: List<CountryDto>? = null,
)
