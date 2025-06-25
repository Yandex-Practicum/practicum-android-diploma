package ru.practicum.android.diploma.ui.filter.place.models

import java.io.Serializable

data class Country(
    val id: String,
    val name: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 122
    }
}
