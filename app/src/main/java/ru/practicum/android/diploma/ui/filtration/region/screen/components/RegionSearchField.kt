package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.SearchQueryField
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction

@Composable
fun RegionSearchField(
    searchQuery: String,
    onAction: (ChooseRegionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    SearchQueryField(
        searchQuery = searchQuery,
        hintRes = R.string.choose_region_search_hint,
        interactionSource = interactionSource,
        onSearchTextChange = { onAction(ChooseRegionAction.SearchQueryChanged(it)) },
        onClear = { onAction(ChooseRegionAction.ClearSearchClicked) },
        onKeyboardDone = { keyboardController?.hide() },
        modifier = modifier,
    )
}
