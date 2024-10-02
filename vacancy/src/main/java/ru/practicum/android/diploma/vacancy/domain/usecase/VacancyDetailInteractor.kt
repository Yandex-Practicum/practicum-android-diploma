package ru.practicum.android.diploma.vacancy.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetail

interface VacancyDetailInteractor {
    fun listVacancy(id: String): Flow<Pair<VacancyDetail?, String?>>
}
