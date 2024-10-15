package ru.practicum.android.diploma.filter.filter.data.repositoryimpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

class FilterSPRepositoryImpl( // убираю internal т.к. repo нужна в search
    private val filterSP: FilterSp,
    private val modelToDtoMapper: ModelToDtoMapper,
    private val dtoToModelMapper: DtoToModelMapper
) : FilterSPRepository {
    override suspend fun clearDataFilter() {
        filterSP.clearDataFilter()
    }

    override suspend fun getBranchOfProfessionDataFilter(): Flow<String?> {
        return flowOf(filterSP.getBranchOfProfessionDataFilter())
    }

    override suspend fun getExpectedSalaryDataFilter(): Flow<Int?> {
        return flowOf(filterSP.getExpectedSalaryDataFilter())
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Flow<Boolean> {
        return flowOf(filterSP.isDoNotShowWithoutSalaryDataFilter())
    }

    override suspend fun getDataFilter(): Flow<FilterSettingsModel> {
        return flowOf(dtoToModelMapper.map(filterSP.getDataFilter()))
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Flow<Int> {
        return flowOf(filterSP.updatePlaceInDataFilter(modelToDtoMapper.map(place)))
    }

    override suspend fun updateProfessionInDataFilter(industryModel: IndustryModel): Flow<Int> {
        return flowOf(filterSP.updateProfessionInDataFilter(industryModel.name))
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: Int): Flow<Int> {
        return flowOf(filterSP.updateSalaryInDataFilter(expectedSalary))
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Flow<Int> {
        return flowOf(filterSP.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary))
    }

    override suspend fun clearSalaryFilter() {
        filterSP.clearSalaryFilter()
    }

    override suspend fun clearPlaceFilter() {
        filterSP.clearPlaceFilter()
    }

    override suspend fun clearIndustryFilter() {
        filterSP.clearIndustryFilter()
    }
}

class DtoToModelMapper { // убрал internal
    fun map(
        dto: FilterDto
    ): FilterSettingsModel {
        return FilterSettingsModel(
            dto.placeDto?.nameCountry,
            dto.placeDto?.nameRegion,
            dto.branchOfProfession,
            dto.expectedSalary,
            dto.doNotShowWithoutSalary
        )
    }

}

class ModelToDtoMapper { // убрал internal
    fun map(
        model: Place
    ): PlaceDto {
        return PlaceDto(model.idCountry.toString(), model.nameCountry, model.idRegion.toString(), model.nameRegion)
    }

}
