package ru.practicum.android.diploma.domain.industries.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.industries.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesInteractorImpl(
    private val industriesRepository: IndustriesRepository
) : IndustriesInteractor {

    override fun getIndustriesList(): Flow<Resource<List<Industry>>> = flow {
        emit(industriesRepository.getIndustriesList())
    }
}
