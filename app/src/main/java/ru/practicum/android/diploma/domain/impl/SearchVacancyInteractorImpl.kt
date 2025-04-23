package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.domain.repositories.SearchVacancyRepository

class SearchVacancyInteractorImpl(
    private val repository: SearchVacancyRepository
) : SearchVacancyInteractor {
    override fun searchVacancy(vacancyName: String): Flow<Resource<List<VacancyShort>>> {
        return repository.searchVacancy(vacancyName).map { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    if (data.isEmpty()) {
                        Resource.Empty()
                    } else {
                        Resource.Success(data)
                    }
                }

                is Resource.Error -> Resource.Error(result.message)
                else -> Resource.Empty()
            }
        }
    }

    override fun searchVacancyDetails(vacancyId: String): Flow<Pair<VacancyLong?, String?>> {
        return repository.searchVacancyDetails(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
                is Resource.Empty -> Pair(null, null)
            }
        }
    }
}
