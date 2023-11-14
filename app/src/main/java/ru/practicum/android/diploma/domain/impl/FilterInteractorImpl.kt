package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.DirectoryRepository
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.Resource

class FilterInteractorImpl(
    private val repository: FilterRepository,
    private val directoryRepository: DirectoryRepository
) : FilterInteractor {

    override fun setSalary(input: String) {
        repository.setSalary(input)
    }

    override fun getSalary(): String {
        return repository.getSalary()
    }

    override fun getAreas(areaId: String): Flow<DataResponse<Area>> {
        return directoryRepository.getAreas(areaId).map { result ->
            when (result) {
                is Resource.Success -> {
                    DataResponse(data = result.data, networkError = null)
                }

                is Resource.Error -> {
                    DataResponse(data = null, networkError = result.message)
                }
            }
        }
    }

    override fun getCountries(): Flow<Pair<List<Country>?, String?>> {
        return directoryRepository.getCountries().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun setSelectedCountry(country: Country?) {
        repository.setSelectedCountry(country)
    }

    override fun getSelectedCountry(): Country? {
        return repository.getSelectedCountry()
    }

    override fun setSelectedArea(area: Area?) {
        repository.setSelectedArea(area)
    }

    override fun getSelectedArea(): Area? {
        return repository.getSelectedArea()
    }

    override fun getIndustries(): Flow<DataResponse<Industry>> {
        return directoryRepository.getIndustries().map { result ->
            when (result) {
                is Resource.Success -> {
                    DataResponse(data = result.data, networkError = null)
                }

                is Resource.Error -> {
                    DataResponse(data = null, networkError = result.message)
                }
            }
        }
    }

    override fun setSelectedIdustries(industry: Industry?) {
        repository.setSelectedIndustry(industry)
    }

    override fun getSelectedIndustries(): Industry? {
        return repository.getSelectedIndustry()
    }

}
