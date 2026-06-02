package ru.practicum.android.diploma.industry.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.Button
import ru.practicum.android.diploma.core.ui.utils.LoadingContent
import ru.practicum.android.diploma.industry.ui.mock.IndustryPreviewProvider
import ru.practicum.android.diploma.search.ui.components.SearchBar

@Composable
fun IndustryScreen(
    viewModel: IndustryViewModel,
    industryId: String?,
    onBack: () -> Unit
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var currentIndustry by remember { mutableStateOf<Industry?>(null) }

    AppScreen(R.string.industry_screen_title, onBack) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = query,
                onFocusChanged = {},
                onIconClicked = viewModel::onSearchIconClicked,
                showClearButton = state.showClearButton,
                onQueryChanged = viewModel::onQueryChanged,
                placeholderStringResource = R.string.search_placeholder_industry
            )
            Box(Modifier.weight(1f)) {
                IndustryContent(state, industryId, { industry ->
                    currentIndustry = industry
                })
            }
            if (currentIndustry != null) {
                Button(
                    text = stringResource(R.string.choose),
                    modifier = Modifier
                        .padding(horizontal = Dimens.padding16)
                        .padding(bottom = Dimens.padding24),
                    onClick = {
                        val industry = currentIndustry
                        if (industry != null) {
                            viewModel.applyFilter(industry)
                            onBack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun IndustryContent(state: IndustryScreenState, industryId: String?, onIndustrySelected: (Industry) -> Unit) {
    var selectedIndustryId by remember { mutableStateOf<String?>(industryId) }
    val keyboardController = LocalSoftwareKeyboardController.current

    when (state) {
        is IndustryScreenState.Default -> {}
        is IndustryScreenState.Content -> {
            val content = state.industries
            IndustryList(
                modifier = Modifier.padding(top = Dimens.padding8),
                industries = content,
                selectedIndustryId = selectedIndustryId,
                onIndustrySelected = { industry ->
                    selectedIndustryId = industry.id
                    onIndustrySelected(industry)
                    keyboardController?.hide()
                }
            )

        }
        is IndustryScreenState.Loading -> LoadingContent()
        is IndustryScreenState.Error -> {}
    }
}

@Composable
private fun IndustryList(
    modifier: Modifier = Modifier,
    industries: List<Industry>,
    selectedIndustryId: String?,
    onIndustrySelected: (Industry) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            count = industries.count(),
            key = { index -> industries[index].id },
            contentType = { index -> Industry }
        ) { index ->
            val industry = industries[index]
            val isSelected = industry.id == selectedIndustryId

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = Dimens.padding16, end = Dimens.padding4)
                    .padding(vertical = Dimens.padding6)
                    .clickable {
                        onIndustrySelected(industry)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = industry.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(Dimens.icon48),
                    contentAlignment = Alignment.Center
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onIndustrySelected(industry) },
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun IndustryScreenPreview(
    @PreviewParameter(IndustryPreviewProvider::class) model: IndustryViewModel
) {
    AppTheme {
        IndustryScreen(model, "7", {})
    }
}
