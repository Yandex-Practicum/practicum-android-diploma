package ru.practicum.android.diploma.ui.filtration.region.action

import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi

sealed interface ChooseRegionAction {

    data object BackClicked : ChooseRegionAction

    data class SearchQueryChanged(
        val query: String,
    ) : ChooseRegionAction

    data object ClearSearchClicked : ChooseRegionAction

    data class RegionClicked(
        val region: RegionUi,
    ) : ChooseRegionAction
}
