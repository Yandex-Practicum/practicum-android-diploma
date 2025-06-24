package ru.practicum.android.diploma.ui.filter.place.models

import java.io.Serializable

data class Region(
    val id: String,
    val name: String,
    val country: Country?,
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 123
    }
}
