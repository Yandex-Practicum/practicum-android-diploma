package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(private val repository: VacancyRepository) : VacancyInteractor {

    override fun getVacancyId(id: String): Flow<Pair<VacancyFullItemDto?, String?>> {
        return repository.getVacancyId(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
