package ru.practicum.android.diploma.domain.detail

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.detail.VacancyDetail

interface DetailInteractor {

    fun searchDetailInformation(vacancyId: String): Flow<Pair<VacancyDetail?, Int?>>
}
