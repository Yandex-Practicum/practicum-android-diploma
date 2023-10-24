package ru.practicum.android.diploma.filter.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Areas
import ru.practicum.android.diploma.common.domain.model.filter_models.Countries
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.domain.model.filter_models.Industries
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.common.util.Resource
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.NO_CONNECTION
import ru.practicum.android.diploma.common.util.constants.RepositoryConst.RESPONSE_SUCCESS
import ru.practicum.android.diploma.filter.data.dataSource.FiltersLocalDataSource
import ru.practicum.android.diploma.filter.data.dataSourceImpl.FiltersLocalStorageDataSourceImpl
import ru.practicum.android.diploma.filter.data.mapper.FiltersDtoToDomainConverter
import ru.practicum.android.diploma.filter.data.model.AllAreasRequest
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CertainAreasRequest
import ru.practicum.android.diploma.filter.data.model.CountriesRequest
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesRequest
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse
import ru.practicum.android.diploma.filter.domain.repository.FiltersRepository
import ru.practicum.android.diploma.search.data.dataSource.VacancyRemoteDataSource
import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource

class FiltersRepositoryImpl(
    private val filtersLocalDataSourceImpl: FiltersLocalDataSource,
    private val filtersLocalStorageDataSourceImpl: FiltersLocalStorageDataSourceImpl,
    private val filtersRemoteDataSource: VacancyRemoteDataSource,
    private val filtersDtoToDomainConverter: FiltersDtoToDomainConverter
) : FiltersRepository {
    override fun getAreas(areaId: String?): Flow<Resource<Areas>> = flow {

        val responseAreas = if (areaId == null) {
            filtersRemoteDataSource.doRequest(AllAreasRequest())
        } else filtersRemoteDataSource.doRequest(CertainAreasRequest(areaId))

        val responseCountries = filtersRemoteDataSource.doRequest(CountriesRequest())

        if (responseAreas.resultCode == NO_CONNECTION || responseCountries.resultCode == NO_CONNECTION) {
            emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))

        } else if (responseAreas.resultCode == RESPONSE_SUCCESS || responseCountries.resultCode == RESPONSE_SUCCESS) {

            val areas: List<AreaFilter> =
                filtersDtoToDomainConverter.convertAreaResponseToListOfAreaWithoutCountries(
                    responseAreas as AreasResponse, responseCountries as CountriesResponse
                )
            emit(Resource.Success(Areas(areas)))

        } else emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
    }

    override fun getCountries(): Flow<Resource<Countries>> = flow {
        val response = filtersRemoteDataSource.doRequest(CountriesRequest())
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val countries: List<CountryFilter> =
                    filtersDtoToDomainConverter.convertCountriesResponseToListOfCountries(response as CountriesResponse)
                emit(Resource.Success(Countries(countries)))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun getIndustries(): Flow<Resource<Industries>> = flow {
        val response = filtersRemoteDataSource.doRequest(IndustriesRequest())
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(ErrorRemoteDataSource.NO_CONNECTION))
            }

            RESPONSE_SUCCESS -> {
                val industries: List<IndustryFilter> =
                    filtersDtoToDomainConverter.convertIndustryResponseToListOfIndustries(response as IndustriesResponse)
                emit(Resource.Success(Industries(industries)))
            }

            else -> {
                emit(Resource.Error(ErrorRemoteDataSource.ERROR_OCCURRED))
            }
        }
    }

    override fun getFilterOptions(): Filter? {
        return if (filtersLocalDataSourceImpl.getFilterOptions() == null) {
            filtersLocalStorageDataSourceImpl.getFilterOptions()
        } else {
            filtersLocalDataSourceImpl.getFilterOptions()
        }
    }

    override fun setFilterOptionsToStorage(options: Filter) {
        filtersLocalStorageDataSourceImpl.setFilterOptions(options)
        filtersLocalDataSourceImpl.clearFilterOptions()
    }

    override fun setCountry(country: CountryFilter) {
        filtersLocalDataSourceImpl.setCountry(country)
    }

    override fun setArea(area: AreaFilter) {
        filtersLocalDataSourceImpl.setArea(area)
    }

    override fun setIndustry(industry: IndustryFilter) {
        filtersLocalDataSourceImpl.setIndustry(industry)
    }

    override fun setSalary(salary: Int) {
        filtersLocalDataSourceImpl.setSalary(salary)
    }

    override fun setOnlyWithSalary(option: Boolean) {
        filtersLocalDataSourceImpl.setOnlyWithSalary(option)
    }

    override fun clearFilterOptions() {
        filtersLocalStorageDataSourceImpl.clearFilterOptions()
    }

    override fun clearArea() {
        filtersLocalDataSourceImpl.clearArea()
    }

    override fun clearIndustry() {
        filtersLocalDataSourceImpl.clearIndustry()
    }

    override fun clearSalary() {
        filtersLocalDataSourceImpl.clearSalary()
    }

    override fun clearTempFilterOptions() {
        filtersLocalDataSourceImpl.clearTempFilterOptions()
    }

    override fun isTempFilterOptionsEmpty(): Boolean {
        return filtersLocalDataSourceImpl.isTempFilterOptionsEmpty()
    }

    override fun isTempFilterOptionsExists(): Boolean {
        return filtersLocalDataSourceImpl.isTempFilterOptionsExists()
    }

    override fun setFilterOptionsToCache(filter: Filter?) {
        filtersLocalDataSourceImpl.setFilterOptions(filter)
    }

    override fun getChosenIndustry(): IndustryFilter? {
        return filtersLocalDataSourceImpl.getFilterOptions()?.industry
    }

    override fun getChosenArea(): AreaFilter? {
        return filtersLocalDataSourceImpl.getFilterOptions()?.area
    }

    override fun getChosenCountry(): CountryFilter? {
        return filtersLocalDataSourceImpl.getFilterOptions()?.country
    }
}