package ru.practicum.android.diploma.search.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor

internal class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(
        page: String,
        perPage: String,
        queryText: String,
        industry: String?,
        salary: String?,
        area: String?,
        only_with_salary: Boolean,
    ): Flow<Pair<PaginationInfo?, String>> {
        return repository.searchVacancies(page, perPage, queryText, industry, salary, area, only_with_salary)
            .map { result ->
                when (result) {
                    is Resource.Success -> {
                        Pair(result.data, "")
                    }

                    is Resource.Error -> {
                        Pair(null, result.message ?: "")
                    }
                }
            }
    }

    override fun listVacancy(id: String): Flow<Pair<VacancyDetail?, String?>> {
        return repository.listVacancy(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }

    override fun listAreas(): Flow<Pair<RegionList?, String?>> {
        return repository.listAreas().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }

    override fun listIndustries(): Flow<Pair<List<IndustryList>?, String?>> {
        return repository.listIndustries().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }
}
