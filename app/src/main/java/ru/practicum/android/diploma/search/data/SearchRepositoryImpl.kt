package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.domain.model.VacancyFilter

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun getAreas(): Flow<Resource<List<FilterAreaDto>>> = flow {
        emit(networkClient.getAreas())
    }

    override fun getIndustry(): Flow<Resource<List<FilterIndustryDto>>> = flow {
        emit(networkClient.getIndustry())
    }

    override fun getVacancies(
        vacancyFilter: VacancyFilter
    ): Flow<Resource<VacancyResponseDto>> = flow {
        emit(
            networkClient.getVacancies(
                vacancyFilter
            )
        )
    }

    override fun getVacancyById(id: String): Flow<Resource<VacancyDetailDto>> = flow {
        emit(networkClient.getVacancyById(id))
    }
}
