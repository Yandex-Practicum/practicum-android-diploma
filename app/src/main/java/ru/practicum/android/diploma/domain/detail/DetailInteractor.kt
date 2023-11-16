package ru.practicum.android.diploma.domain.detail

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

interface DetailInteractor {
    fun getVacancy(id: String): Flow<Pair<FullVacancy?, String?>>
    fun sharePhone(phone: Phone)
    fun shareEmail(email: String)
    fun shareVacancyUrl(vacancyUrl: String)
}
