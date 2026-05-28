package ru.practicum.android.diploma.filter.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.ListItem
import ru.practicum.android.diploma.core.ui.utils.Spacer
import ru.practicum.android.diploma.filter.ui.components.CancellableFilterListItem
import ru.practicum.android.diploma.filter.ui.components.SalaryTextField
import ru.practicum.android.diploma.filter.ui.mock.FilterPreviewProvider
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.mock.SearchPreviewProvider

@Composable
fun FilterScreen(
    viewModel: FilterViewModel,
    onNavigateToArea: () -> Unit,
    onNavigateToIndustry: () -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    AppScreen(R.string.filter_screen_title, onBack) {
        Column(modifier = Modifier.padding(Dimens.padding16)) {
            CancellableFilterListItem(
                label = stringResource(R.string.filter_area_label),
                value = state.value.area,
                onReset = viewModel::onResetArea,
                onNavigate = onNavigateToArea
            )

            CancellableFilterListItem(
                label = stringResource( R.string.filter_industry_label),
                value = state.value.industry,
                onReset = viewModel::onResetIndustry,
                onNavigate = onNavigateToIndustry
            )

            Spacer(height = Dimens.padding24)
            SalaryTextField(state.value.salary ?: "",
                viewModel::onQueryChanged,
                viewModel::onFocusChanged
            )
            Spacer(height = Dimens.padding24)

            ListItem(
                title = stringResource( R.string.filter_salary_checkbox),
                icon = if (state.value.onlyWithSalary) R.drawable.ic_core_check_box_on else R.drawable.ic_core_check_box_off,
                iconTint = Blue
            )

        }
    }
}



@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FilterScreenPreview(
    @PreviewParameter(FilterPreviewProvider::class) model: FilterViewModel
) {
    AppTheme {
        FilterScreen(model,{}, {},{})
    }
}

