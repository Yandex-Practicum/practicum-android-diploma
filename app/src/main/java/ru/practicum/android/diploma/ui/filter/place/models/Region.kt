package ru.practicum.android.diploma.ui.filter.place.models

import java.io.Serializable

data class Region(
    val id: String,
    val name: String,
    val country: Country?,
    private val serialVersionUID: Long = 1L,
) : Serializable
