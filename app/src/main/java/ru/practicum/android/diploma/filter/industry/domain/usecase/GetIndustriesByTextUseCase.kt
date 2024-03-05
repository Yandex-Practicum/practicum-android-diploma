package ru.practicum.android.diploma.filter.industry.domain.usecase

import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filter.industry.domain.api.IndustryRepository
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryError
import ru.practicum.android.diploma.util.Result

class GetIndustriesByTextUseCase(
    private val industryRepository: IndustryRepository
) {
    /**
     * @param searchText Строка по которой будет идти поиск, если пустая, то результатом будет весь доступный список
     */
    fun execute(searchText: String) = industryRepository.getIndustries().map { result ->
        if (result is Result.Success) {
            val filteredIndustries = result.data.filter { it.name.startsWith(searchText, ignoreCase = true) }
            if (filteredIndustries.isEmpty()) {
                Result.Error(IndustryError.NotFound)
            } else {
                Result.Success(filteredIndustries)
            }
        } else {
            result
        }
    }
}
