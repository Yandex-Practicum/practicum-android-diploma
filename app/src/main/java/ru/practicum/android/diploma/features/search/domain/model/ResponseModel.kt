package ru.practicum.android.diploma.features.search.domain.model

import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel

data class ResponseModel(
    val error: QueryError,
    val resultVacancyList: ArrayList<VacancyScreenModel>
)