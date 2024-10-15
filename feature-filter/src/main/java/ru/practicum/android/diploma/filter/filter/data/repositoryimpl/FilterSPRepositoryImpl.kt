package ru.practicum.android.diploma.filter.filter.data.repositoryimpl

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

internal class FilterSPRepositoryImpl(
    private val filterSP: FilterSp,
    private val modelToDtoMapper: ModelToDtoMapper,
    private val dtoToModelMapper: DtoToModelMapper
) : FilterSPRepository {
    override suspend fun clearDataFilter() {
        filterSP.clearDataFilter()
    }


    override suspend fun getBranchOfProfessionDataFilter(): String? {
        return filterSP.getBranchOfProfessionDataFilter()
    }

    override suspend fun getExpectedSalaryDataFilter(): Int? {
        return filterSP.getExpectedSalaryDataFilter()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return filterSP.isDoNotShowWithoutSalaryDataFilter()
    }

    override suspend fun getDataFilter(): FilterSettingsModel {
        return dtoToModelMapper.map(filterSP.getDataFilter())
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Int {
        return filterSP.updatePlaceInDataFilter(modelToDtoMapper.map(place))
    }

    override suspend fun updateProfessionInDataFilter(industryModel: IndustryModel): Int {
        return filterSP.updateProfessionInDataFilter(industryModel.name)
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: Int): Int {
        return filterSP.updateSalaryInDataFilter(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return filterSP.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
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

internal class DtoToModelMapper {
    fun map(
        dto: FilterDto
    ): FilterSettingsModel {
        return FilterSettingsModel(
            dto.placeDto?.nameCountry,
            dto.placeDto?.nameRegion, dto.branchOfProfession, dto.expectedSalary, dto.doNotShowWithoutSalary
        )
    }

}

internal class ModelToDtoMapper {
    fun map(
        model: Place
    ): PlaceDto {
        return PlaceDto(model.idCountry.toString(),model.nameCountry,model.idRegion.toString(),model.nameRegion)
    }

}
