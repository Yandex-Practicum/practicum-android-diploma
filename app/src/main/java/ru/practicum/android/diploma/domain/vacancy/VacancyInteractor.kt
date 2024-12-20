package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

interface VacancyInteractor {

    fun getVacancyId(id: String): Flow<Pair<VacancyFullItemDto?, String?>>
}
