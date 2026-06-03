package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.common.FilterItem
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.theme.Dimens

private val RegionListTopPadding = 8.dp

@Composable
fun RegionList(
    regions: List<RegionUi>,
    onAction: (ChooseRegionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = RegionListTopPadding,
                end = Dimens.ScreenHorizontalPadding,
            ),
    ) {
        items(
            items = regions,
            key = { region -> region.id },
        ) { region ->
            FilterItem(
                title = region.name,
                onItemClick = { onAction(ChooseRegionAction.RegionClicked(region)) },
            )
        }
    }
}
