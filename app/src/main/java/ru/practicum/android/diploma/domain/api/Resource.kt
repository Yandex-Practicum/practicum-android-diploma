package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.main.Vacancy

sealed interface Resource {
    data class NoConnection(val code: Int) : Resource
    data class Content(
        val vacancy : List<Vacancy>
    ) : Resource
    data class Error(val code: Int) : Resource
}
