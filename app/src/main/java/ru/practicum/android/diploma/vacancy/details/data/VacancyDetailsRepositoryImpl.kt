package ru.practicum.android.diploma.vacancy.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository

// возможно нужно удалить репозиторий
class VacancyDetailsRepositoryImpl : VacancyDetailsRepository {
    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> = flow {
    }
}
