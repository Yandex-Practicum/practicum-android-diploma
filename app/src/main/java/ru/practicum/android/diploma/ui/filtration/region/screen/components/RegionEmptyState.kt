package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.R

@Composable
fun RegionEmptyState(
    modifier: Modifier = Modifier,
) {
    RegionPlaceholderState(
        imageRes = R.drawable.img_nothing_found,
        textRes = R.string.choose_region_not_found,
        modifier = modifier.fillMaxSize(),
    )
}
