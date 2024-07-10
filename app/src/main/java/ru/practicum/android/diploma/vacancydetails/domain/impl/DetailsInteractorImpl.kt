package ru.practicum.android.diploma.vacancydetails.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.ErrorType
import ru.practicum.android.diploma.common.domain.Resource
import ru.practicum.android.diploma.vacancydetails.domain.api.DetailsInteractor
import ru.practicum.android.diploma.vacancydetails.domain.api.DetailsRepository
import ru.practicum.android.diploma.vacancydetails.presentation.models.Vacancy

class DetailsInteractorImpl(private val detailsRepository: DetailsRepository) : DetailsInteractor {
    override fun getVacansy(expression: String, page: Int?, perPage: Int?): Flow<Pair<List<Vacancy>?, ErrorType>> {
        return detailsRepository.getVacansy(expression, page, page).map { responce ->
            when (responce) {
                is Resource.Success -> { Pair(responce.data, responce.error) }
                is Resource.Error -> { Pair(null, responce.error) }
            }
        }
    }
}
