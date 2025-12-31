package ru.practicum.android.diploma.vacancy.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.mapper.DtoMapper
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyFakeFactory

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dtoMapper: DtoMapper
) : VacancyDetailsRepository {

    override fun getDetailsFromApi(id: String): Flow<Result<VacancyDetail>> = flow {
        val resource = networkClient.getVacancyById(id)
        when (resource) {
            is Resource.Success -> {
                val vacancyDetail = dtoMapper.vacancyDetailDtoToDomain(resource.data)
                emit(Result.Success(vacancyDetail))
            }
            is Resource.Error -> {
                val throwable = resource.exception ?: Exception(resource.message)
                emit(Result.Error(throwable))
            }
        }
    }

    override fun getDetailsFromDataBase(id: String): Flow<Result<VacancyDetail>> = flow {
        emit(Result.Success(VacancyFakeFactory.create()))
    }
}
