package ru.practicum.android.diploma.area.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.area.ui.mock.AreaPreviewProvider
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.Button
import ru.practicum.android.diploma.core.ui.utils.CancellableFilterListItem

@Composable
fun AreaScreen(
    currentEntry: NavBackStackEntry?,
    viewModel: AreaViewModel,
    onNavigateToRegion: (countryId: String?) -> Unit,
    onNavigateToCountry: () -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val countryResult by currentEntry?.savedStateHandle
        ?.getStateFlow<Area?>("country", null)
        ?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }
    val regionResult by currentEntry?.savedStateHandle
        ?.getStateFlow<Area?>("region", null)
        ?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }
    val regionCountryResult by currentEntry?.savedStateHandle
        ?.getStateFlow<Area?>("region_country", null)
        ?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }

    LaunchedEffect(countryResult) {
        countryResult?.let { data ->
            viewModel.onCountrySelected(country = data)
            currentEntry?.savedStateHandle?.remove<Area?>("country")
        }
    }

    LaunchedEffect(regionResult, regionCountryResult) {
        val region = regionResult
        val regionCountry = regionCountryResult
        if (region != null && regionCountry != null) {
            viewModel.onRegionSelected(region = region, regionCountry = regionCountry)
            currentEntry?.savedStateHandle?.remove<Area?>("region")
            currentEntry?.savedStateHandle?.remove<Area?>("region_country")
        }
    }

    AppScreen(R.string.area_screen_title, onBack) {
        Column(modifier = Modifier.padding(top = Dimens.padding16)) {
            CancellableFilterListItem(
                label = stringResource(R.string.area_country),
                value = state.value.country?.name,
                onReset = viewModel::resetCountry,
                onNavigate = onNavigateToCountry
            )

            CancellableFilterListItem(
                label = stringResource(R.string.area_region),
                value = state.value.region?.name,
                onReset = viewModel::resetRegion,
                onNavigate = { onNavigateToRegion(state.value.country?.id) }
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.value.country != null || state.value.region != null) {
                Button(
                    text = stringResource(R.string.area_button_aply),
                    modifier = Modifier.padding(horizontal = Dimens.padding16),
                    onClick = {
                        viewModel.apply()
                        onBack()
                    }
                )
            }
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
        AreaScreen(null, model, { _ -> }, {}, {})
    }
}
