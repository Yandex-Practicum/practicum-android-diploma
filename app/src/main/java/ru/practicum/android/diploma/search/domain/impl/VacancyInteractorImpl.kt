package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class VacancyInteractorImpl(private val repository: VacancyRepository) : VacancyInteractor {
    override fun getAreas(): Flow<Result<List<FilterArea>>> {
        return repository.getAreas().map { resource ->
            when (resource) {
                is Resource.Success -> Result.Success(resource.data.map {
                    FilterArea(
                        it.id,
                        it.name,
                        it.parentId,
                        it.areas.map { FilterArea(it.id, it.name, it.parentId, emptyList()) })
                })

                is Resource.Error -> {
                    Result.Error(resource.message, resource.exception)

                }
            }
        }
    }

    override fun getIndustry(): Flow<Result<List<FilterIndustry>>> {
        return repository.getIndustry().map { resource ->
            when (resource) {
                is Resource.Success -> Result.Success(resource.data.map {
                    FilterIndustry(
                        it.id, it.name
                    )
                })

                is Resource.Error -> {
                    Result.Error(resource.message, resource.exception)
                }
            }
        }
    }

    override fun getVacancies(): Flow<Result<VacancyResponse>> {
        return repository.getVacancies().map { resource ->
            when (resource) {
                is Resource.Success -> Result.Success(
                    VacancyResponse(
                        resource.data.found, resource.data.pages, resource.data.page, resource.data.vacancies.map {
                            VacancyDetail(
                                it.id,
                                it.name,
                                it.description,
                                Salary(it.salary?.from, it.salary?.to, it.salary?.currency),
                                it.address,
                                it.experience,
                                it.schedule,
                                it.employment,
                                it.contacts,
                                it.employer,
                                it.area,
                                it.skills,
                                it.url,
                                it.industry
                            )
                        }
                    )
                )

                is Resource.Error -> {
                    Result.Error(resource.message, resource.exception)
                }
            }
        }
    }

    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> {
        TODO("Not yet implemented")
    }

}
