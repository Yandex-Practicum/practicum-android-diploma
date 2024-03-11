package ru.practicum.android.diploma.domain.detail

import kotlinx.coroutines.flow.Flow

interface DetailInteractor {

    fun searchDetailInformation(vacancyId: String): Flow<Pair<VacancyDetail?, Int?>>
}
