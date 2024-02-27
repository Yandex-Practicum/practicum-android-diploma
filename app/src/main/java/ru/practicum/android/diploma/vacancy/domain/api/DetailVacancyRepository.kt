package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.util.Resource

interface DetailVacancyRepository {
    fun getDetailVacancyById(id: Long): Flow<Resource<DetailVacancy>>
}
