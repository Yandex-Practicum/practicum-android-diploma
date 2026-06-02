package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.theme.Dimens

private val RegionListTopPadding = 8.dp
private val RegionListItemVerticalPadding = 12.dp

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
            RegionListItem(
                region = region,
                onClick = { onAction(ChooseRegionAction.RegionClicked(region)) },
            )
        }
    }
}

@Composable
fun RegionListItem(
    region: RegionUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = RegionListItemVerticalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = region.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        IconImage(
            resId = R.drawable.ic_arrow_forward,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
