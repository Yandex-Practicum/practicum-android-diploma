package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.converter.countryDtoListToAllRegionList
import ru.practicum.android.diploma.filter.data.converter.countryDtoToCountry
import ru.practicum.android.diploma.filter.data.converter.industryDtoListToIndustryList
import ru.practicum.android.diploma.filter.data.converter.mapRegionListDtoToRegionList
import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.filter.data.model.RegionListDto
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage,
    private val apiHelper: AlternativeRemoteDataSource,
) : FilterRepository {
    override suspend fun saveFilterSettings(key: String, selectedFilter: SelectedFilter) {
        sharedPrefsStorage.writeData(key = key, data = selectedFilter)
    }

    override suspend fun getSaveFilterSettings(key: String): SelectedFilter {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.OBJECT)
    }

    override suspend fun getAllCountries(): Either<Failure, List<Country>> {
        return ((apiHelper.doRequest(Request.AllCountriesRequest)) as Either<Failure, List<CountryDto>>).flatMap {
            val list = countryDtoToCountry(it)
            Either.Right(list)
        }
    }

    override suspend fun searchRegions(id: String): Either<Failure, List<Region>> {
        return ((apiHelper.doRequest(Request.RegionRequest(id))) as Either<Failure, RegionListDto>)
            .flatMap { Either.Right(mapRegionListDtoToRegionList(it)) }
    }

    override suspend fun getIndustries(): Either<Failure, List<Industry>> {
        return (apiHelper.doRequest(Request.AllIndustriesRequest) as Either<Failure, List<IndustryDto>>).flatMap {
            Either.Right(industryDtoListToIndustryList(it))
        }
    }

    override suspend fun getAllRegions(): Either<Failure, List<Region>> {
        return ((apiHelper.doRequest(Request.AllCountriesRequest)) as Either<Failure, List<CountryDto>>).flatMap {
            val list = countryDtoListToAllRegionList(it)
            Either.Right(list)
        }
    }
}