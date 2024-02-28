package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

interface DetailRepository {

    fun searchDetailInformation(vacancyId: String): Flow<Resource<VacancyDetail>>
}
