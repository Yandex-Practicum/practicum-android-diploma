package ru.practicum.android.diploma.domain.models

import java.io.Serializable

data class Areas(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<Areas>
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
