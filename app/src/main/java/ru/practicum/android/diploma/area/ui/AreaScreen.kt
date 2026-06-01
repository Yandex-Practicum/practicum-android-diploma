package ru.practicum.android.diploma.area.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.area.ui.mock.AreaPreviewProvider
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.CancellableFilterListItem

@Composable
fun AreaScreen(
    viewModel: AreaViewModel,
    onNavigateToRegion: () -> Unit,
    onNavigateToCountry: () -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    AppScreen(R.string.area_screen_title, onBack) {
        Column(modifier = Modifier.padding(top = Dimens.padding16)) {
            CancellableFilterListItem(
                label = stringResource(R.string.area_country),
                value = state.value.country,
                onReset = viewModel::resetCountry,
                onNavigate = onNavigateToCountry
            )

            CancellableFilterListItem(
                label = stringResource(R.string.area_region),
                value = state.value.region,
                onReset = viewModel::resetRegion,
                onNavigate = onNavigateToRegion
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun AreaScreenPreview(
    @PreviewParameter(AreaPreviewProvider::class) model: AreaViewModel
) {
    AppTheme {
        AreaScreen(model, {}, {}, {})
    }
}
