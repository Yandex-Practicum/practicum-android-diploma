package ru.practicum.android.diploma.ui.filter.place.models

import java.io.Serializable

data class Country(
    val id: String,
    val name: String,
    private val serialVersionUID: Long = 1L,
) : Serializable
