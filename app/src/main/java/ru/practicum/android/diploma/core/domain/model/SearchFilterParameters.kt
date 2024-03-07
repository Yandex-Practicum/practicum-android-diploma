package ru.practicum.android.diploma.core.domain.model

data class SearchFilterParameters(
    val regionId: String = "",
    val industriesId: String = "",
    val salary: String = "",
    val isOnlyWithSalary: Boolean = false
)
