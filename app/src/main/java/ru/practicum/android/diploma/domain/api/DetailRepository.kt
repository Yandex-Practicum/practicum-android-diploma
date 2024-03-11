package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.detail.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface DetailRepository {

    fun searchDetailInformation(vacancyId: String): Flow<Resource<VacancyDetail>>
}
