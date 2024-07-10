package ru.practicum.android.diploma.vacancydetails.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.Resource
import ru.practicum.android.diploma.vacancydetails.presentation.models.Vacancy

interface DetailsRepository {
    fun getVacansy(expression: String, page: Int?, perPage: Int?): Flow<Resource<List<Vacancy>>>
}
