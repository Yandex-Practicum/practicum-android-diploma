package ru.practicum.android.diploma.vacancydetails.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.ErrorType
import ru.practicum.android.diploma.vacancydetails.presentation.models.Vacancy

interface DetailsInteractor {
    fun getVacansy(expression: String, page: Int?, perPage: Int?): Flow<Pair<List<Vacancy>?, ErrorType>>
}
