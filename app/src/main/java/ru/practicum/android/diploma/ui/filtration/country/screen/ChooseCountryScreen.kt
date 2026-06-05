package ru.practicum.android.diploma.ui.filtration.country.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.presentation.filtration.country.state.ChooseCountryUIState
import ru.practicum.android.diploma.ui.common.filter.FilterItem
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun ChooseCountryScreen(
    modifier: Modifier = Modifier,
    state: ChooseCountryUIState,
    onItemClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = {
        TopBar(
            text = stringResource(R.string.choose_country),
            navIconVisible = true,
            onNavClick = onBackClick,
            endFirstIconVisible = false,
            endSecondIconVisible = false,
        )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                is ChooseCountryUIState.Content -> CountriesList(countries = state.countries, onItemClick = onItemClick)
                is ChooseCountryUIState.Error -> CountryError()
                ChooseCountryUIState.Loading -> Loader(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun CountriesList(modifier: Modifier = Modifier, countries: List<FilterArea>, onItemClick: () -> Unit) {
    LazyColumn(modifier = modifier) {
        items(items = countries, key = { country -> country.id }) { country ->
            FilterItem(
                title = country.name,
                onItemClick = onItemClick,
                headlineContentColorInverted = true
            )
        }
    }
}

@Composable
private fun CountryError() {
    PlaceholderLayout(
        imageRes = R.drawable.img_no_results,
        textRes = R.string.choose_country_error
    )
}

private val stateError = ChooseCountryUIState.Error
private val stateContent = ChooseCountryUIState.Content(
    countries = MocData.countries
)

@Preview(showSystemUi = true)
@Composable
private fun ChooseCountryScreenPreview() {
    AppTheme {
        ChooseCountryScreen(
            state = stateContent,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChooseCountryScreenPreviewError() {
    AppTheme {
        ChooseCountryScreen(
            state = stateError,
        )
    }
}
