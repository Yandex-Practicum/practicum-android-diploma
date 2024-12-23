package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(private val repository: IndustriesRepository) : IndustriesInteractor {

    override fun getIndustries(): Flow<List<IndustriesFullDto>> {
        return repository.getIndustries()
            .map { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data ?: emptyList()
                    }

                    is Resource.Error -> {
                        emptyList()
                    }
                }
            }
    }
}
