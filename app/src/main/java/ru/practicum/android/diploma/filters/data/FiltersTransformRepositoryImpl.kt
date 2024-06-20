package ru.practicum.android.diploma.filters.data

import ru.practicum.android.diploma.data.dto.responses.areas.AreaUnitDTO
import ru.practicum.android.diploma.data.dto.responses.industry.IndustryUnitDTO
import ru.practicum.android.diploma.filters.domain.FiltersTransformRepository
import ru.practicum.android.diploma.search.data.mapper.AreaMapper
import ru.practicum.android.diploma.search.data.mapper.IndustryMapper
import ru.practicum.android.diploma.search.domain.model.fields.Area
import ru.practicum.android.diploma.search.domain.model.fields.Industry

class FiltersTransformRepositoryImpl(
    private val areaMapper: AreaMapper,
    private val industryMapper: IndustryMapper,
    private val regionRecursionLimit: Int = 1,
    private val countryRecursionLimit: Int = 1,
    private val industryRecursionLimit: Int = 0,
) : FiltersTransformRepository {
    override fun regionsFromDTO(input: List<AreaUnitDTO>): List<Area>  = areaMapper.map(input, regionRecursionLimit)

    override fun countriesFromDTO(input: List<AreaUnitDTO>): List<Area> = areaMapper.map(input, countryRecursionLimit)

    override fun industriesFromDTO(input: List<IndustryUnitDTO>): List<Industry>  = industryMapper.map(input, industryRecursionLimit)
}
