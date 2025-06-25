package ru.practicum.android.diploma.ui.main.utils

import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters

fun SelectedFilters.toFilterOptions(searchText: String, currency: String?, page: Int): FilterOptions {
    return FilterOptions(
        searchText = searchText,
        area = region?.id ?: country?.id.orEmpty(),
        industry = industryId.orEmpty(),
        salary = salary,
        onlyWithSalary = onlyWithSalary,
        page = page
    )
}
