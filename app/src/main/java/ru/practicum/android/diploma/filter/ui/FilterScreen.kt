package ru.practicum.android.diploma.filter.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import ru.practicum.android.diploma.core.ui.utils.Button
import ru.practicum.android.diploma.core.ui.utils.ButtonType
import ru.practicum.android.diploma.core.ui.utils.CancellableFilterListItem
import ru.practicum.android.diploma.core.ui.utils.ListItem
import ru.practicum.android.diploma.core.ui.utils.Spacer
import ru.practicum.android.diploma.filter.ui.components.SalaryTextField
import ru.practicum.android.diploma.filter.ui.mock.FilterPreviewProvider

@Composable
fun FilterScreen(
    viewModel: FilterViewModel,
    onNavigateToArea: () -> Unit,
    onNavigateToIndustry: (String?) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val isModified = viewModel.isModified.collectAsState()
    val isFiltered = viewModel.isFiltered.collectAsState()

    AppScreen(R.string.filter_screen_title, {
        viewModel.cancel()
        onBack()
    }) {
        Column(modifier = Modifier.padding(bottom = Dimens.padding24)) {
            CancellableFilterListItem(
                label = stringResource(R.string.filter_area_label),
                value = state.value.region?.let { region ->
                    listOfNotNull(state.value.country?.name, region.name).joinToString(", ")
                } ?: state.value.country?.name,
                onReset = viewModel::onResetArea,
                onNavigate = onNavigateToArea
            )

            CancellableFilterListItem(
                label = stringResource(R.string.filter_industry_label),
                value = state.value.industry?.name,
                onReset = viewModel::onResetIndustry,
                onNavigate = { onNavigateToIndustry(state.value.industry?.id) }
            )

            SalaryTextField(
                state.value.salary ?: "",
                modifier = Modifier.padding(
                    horizontal = Dimens.padding16,
                    vertical = Dimens.padding24
                ),
                viewModel::onQueryChanged,
                viewModel::onResetSalary,
            )

            ListItem(
                title = stringResource(R.string.filter_salary_checkbox),
                icon = if (state.value.onlyWithSalary) {
                    R.drawable.ic_core_check_box_on
                } else {
                    R.drawable.ic_core_check_box_off
                },
                iconTint = Blue,
                onClickIcon = viewModel::onToggleSalary
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isModified.value) {
                Button(
                    text = stringResource(R.string.filter_apply),
                    modifier = Modifier.padding(horizontal = Dimens.padding16),
                    onClick = {
                        viewModel.apply()
                        onBack()
                    }
                )
            }
            if (isFiltered.value) {
                Button(
                    text = stringResource(R.string.filter_reset),
                    type = ButtonType.TERTIARY,
                    modifier = Modifier.padding(horizontal = Dimens.padding16),
                    onClick = {
                        viewModel.reset()
                    }
                )
            }

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
        FilterScreen(model, {}, {}, {})
    }
}
