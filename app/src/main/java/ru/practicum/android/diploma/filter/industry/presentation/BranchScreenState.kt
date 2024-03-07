package ru.practicum.android.diploma.filter.industry.presentation

import ru.practicum.android.diploma.filter.industry.domain.model.Industry

sealed class BranchScreenState {
    class Content(
        val branches: ArrayList<Industry>,
        val selectedIndustry: Industry?,
        val selectedIndex: Int?
    ) : BranchScreenState()

    data object Error : BranchScreenState()
}
