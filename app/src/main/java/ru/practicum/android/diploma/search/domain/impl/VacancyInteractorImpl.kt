package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.data.mapper.Mapper
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class VacancyInteractorImpl(private val repository: VacancyRepository, val mapper: Mapper) : VacancyInteractor {
    override fun getAreas(): Flow<Result<List<FilterArea>>> {
        return repository.getAreas().map { resource ->
            when (resource) {
                is Resource.Success -> Result.Success(resource.data.map {
                    mapper.fromDto(it)
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
                    mapper.fromDto(it)
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
                    mapper.fromDto(resource.data)
                )


                is Resource.Error -> {
                    Result.Error(resource.message, resource.exception)
                }
            }
        }
    }

    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> {
        return repository.getVacancyById(id).map { resource ->
            when (resource) {
                is Resource.Success -> Result.Success(
                    mapper.fromDto(resource.data)
                )

                is Resource.Error -> {
                    Result.Error(resource.message, resource.exception)
                }
            }
        }
    }

}
