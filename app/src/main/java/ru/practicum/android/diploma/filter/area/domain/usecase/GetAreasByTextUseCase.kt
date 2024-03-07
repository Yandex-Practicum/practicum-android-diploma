package ru.practicum.android.diploma.filter.area.domain.usecase

import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filter.area.domain.api.AreaRepository
import ru.practicum.android.diploma.filter.area.domain.model.AreaError
import ru.practicum.android.diploma.util.Result

class GetAreasByTextUseCase(
    private val areaRepository: AreaRepository
) {
    /**
     * @param searchText Строка по которой будет идти поиск, если пустая, то результатом будет весь доступный список
     * @param countryId Id страны, если пустой, то весь список без стран
     */

    fun execute(searchText: String, countryId: String) = areaRepository.getAreas(countryId).map { result ->
        if (result is Result.Success) {
            val filteredAreas = result.data.filter { it.name.startsWith(searchText, ignoreCase = true) }
            if (filteredAreas.isEmpty()) {
                Result.Error(AreaError.NotFound)
            } else {
                Result.Success(filteredAreas)
            }
        } else {
            result
        }
    }
}
