package ru.practicum.android.diploma.search.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.domain.api.VacancyRepository

class VacancyRepositoryImpl(private val networkClientImpl: NetworkClientImpl) : VacancyRepository {
    override fun getAreas(): Flow<Resource<List<FilterAreaDto>>> = flow {
        emit(networkClientImpl.getAreas())
    }

    override fun getIndustry(): Flow<Resource<List<FilterIndustryDto>>> = flow {
        emit(networkClientImpl.getIndustry())
    }

    override fun getVacancies(): Flow<Resource<VacancyResponseDto>> = flow {
        emit(networkClientImpl.getVacancies())
    }

    override fun getVacancyById(id: String): Flow<Resource<VacancyDetailDto>> = flow {
        emit(networkClientImpl.getVacancyById(id))
    }
}
