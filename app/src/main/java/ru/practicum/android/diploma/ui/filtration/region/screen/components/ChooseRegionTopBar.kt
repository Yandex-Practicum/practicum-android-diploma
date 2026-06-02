package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction

@Composable
fun ChooseRegionTopBar(
    onAction: (ChooseRegionAction) -> Unit,
) {
    TopBar(
        text = stringResource(R.string.choose_region_title),
        navIconVisible = true,
        onNavClick = { onAction(ChooseRegionAction.BackClicked) },
        endFirstIconVisible = false,
        endSecondIconVisible = false,
    )
}
