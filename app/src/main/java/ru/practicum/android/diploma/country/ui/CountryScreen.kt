package ru.practicum.android.diploma.country.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.ListItem
import ru.practicum.android.diploma.core.ui.utils.LoadingContent
import ru.practicum.android.diploma.core.ui.utils.Stub
import ru.practicum.android.diploma.country.ui.mock.CountryPreviewProvider

@Composable
fun CountryScreen(viewModel: CountryViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()

    AppScreen(R.string.country_screen_title, onBack) {
        when (state) {
            is CountryScreenState.Loading -> LoadingContent()
            is CountryScreenState.Content -> {
                LazyColumn(modifier = Modifier.padding(top = Dimens.padding8)) {
                    items((state as CountryScreenState.Content).items, key = { it.id }) { country ->
                        ListItem(
                            title = country.name,
                            icon = R.drawable.ic_core_arrow_forward,
                            onClickItem = {
                                viewModel.selectCountry(country)
                                onBack()
                            }
                        )
                    }
                }
            }
            is CountryScreenState.Error -> {
                Stub(
                    if ((state as CountryScreenState.Error).error == CountryError.NO_INTERNET) {
                        R.drawable.image_core_stub_no_internet
                    } else {
                        R.drawable.image_core_stub_empty_list
                    },
                    if ((state as CountryScreenState.Error).error == CountryError.NO_INTERNET) {
                        R.string.no_internet_connection
                    } else {
                        R.string.country_stub_empty_list
                    }
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CountryScreenPreview(
    @PreviewParameter(CountryPreviewProvider::class) model: CountryViewModel
) {
    AppTheme {
        CountryScreen(model, {})
    }
}
