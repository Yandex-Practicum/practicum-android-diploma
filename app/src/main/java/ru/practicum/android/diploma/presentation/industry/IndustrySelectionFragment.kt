@file:Suppress("LongMethod")

package ru.practicum.android.diploma.presentation.industry

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.ui.components.EmptyState
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.WhiteUniversal

class IndustrySelectionFragment : Fragment() {

    private val viewModel by viewModel<IndustrySelectionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val state by viewModel.uiState.collectAsState()
                    IndustrySelectionScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onSearchQueryChanged = viewModel::onSearchQueryChanged,
                        onIndustryClicked = viewModel::onIndustryClicked,
                        onSelectClicked = viewModel::onSelectClicked,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedIndustry.collect { industry ->
                    parentFragmentManager.setFragmentResult(
                        INDUSTRY_SELECTED_REQUEST_KEY,
                        Bundle().apply {
                            putInt(INDUSTRY_ID_KEY, industry.id)
                            putString(INDUSTRY_NAME_KEY, industry.name)
                        }
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }

    companion object {
        const val INDUSTRY_SELECTED_REQUEST_KEY = "industrySelected"
        const val INDUSTRY_ID_KEY = "industryId"
        const val INDUSTRY_NAME_KEY = "industryName"
    }
}

@Composable
private fun IndustrySelectionScreen(
    state: IndustrySelectionUiState,
    onBackClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onIndustryClicked: (Industry) -> Unit,
    onSelectClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        IndustryTopBar(onBackClicked = onBackClicked)
        Spacer(modifier = Modifier.height(Dimens.paddingDefault))

        when (state) {
            is IndustrySelectionUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Blue)
                }
            }

            is IndustrySelectionUiState.ServerError -> {
                EmptyState(
                    imageRes = R.drawable.il_main_error_server_328,
                    title = stringResource(R.string.vacancy_error_server),
                )
            }

            is IndustrySelectionUiState.NetworkError -> {
                EmptyState(
                    imageRes = R.drawable.il_main_no_internet_328,
                    title = stringResource(R.string.search_error_no_internet),
                )
            }

            is IndustrySelectionUiState.Content -> {
                IndustrySearchField(
                    query = state.searchQuery,
                    onQueryChanged = onSearchQueryChanged,
                    modifier = Modifier.padding(horizontal = Dimens.paddingDefault),
                )
                Spacer(modifier = Modifier.height(Dimens.paddingDefault))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.filtered, key = { it.id }) { industry ->
                        IndustryItem(
                            industry = industry,
                            isSelected = industry == state.selectedIndustry,
                            onClick = { onIndustryClicked(industry) },
                        )
                    }
                }
                if (state.canConfirm) {
                    SelectButton(
                        onClick = onSelectClicked,
                        modifier = Modifier.padding(Dimens.paddingDefault),
                    )
                }
            }
        }
    }
}

@Composable
private fun IndustryTopBar(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.paddingTopLarge,
                start = Dimens.paddingSmall,
                end = Dimens.paddingDefault,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(modifier = Modifier.width(Dimens.paddingSystemBar))
        Text(
            text = stringResource(R.string.filter_industry_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
private fun IndustrySearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.searchFieldHeight)
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = Dimens.paddingDefault),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = Dimens.iconSizeMedium),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
            ),
            cursorBrush = SolidColor(Blue),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box {
                    if (query.isBlank()) {
                        Text(
                            text = stringResource(R.string.filter_industry_hint),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            ),
                        )
                    }
                    innerTextField()
                }
            },
        )
        Icon(
            painter = painterResource(
                if (query.isNotBlank()) R.drawable.ic_close_24 else R.drawable.ic_search_24
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(enabled = query.isNotBlank()) { onQueryChanged("") },
        )
    }
}

@Composable
private fun IndustryItem(
    industry: Industry,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.heightMenuItem)
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.paddingDefault),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = industry.name,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
        Icon(
            painter = painterResource(
                if (isSelected) R.drawable.ic_radio_button_on_24 else R.drawable.ic_radio_button_off_24
            ),
            contentDescription = null,
            tint = Blue,
        )
    }
}

@Composable
private fun SelectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.heightButton),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = WhiteUniversal,
        ),
        shape = RoundedCornerShape(Dimens.cornerRadius),
    ) {
        Text(
            text = stringResource(R.string.filter_industry_apply),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

private val previewIndustries = listOf(
    Industry(id = 1, name = "Информационные технологии"),
    Industry(id = 2, name = "Финансы и банки"),
    Industry(id = 3, name = "Строительство и недвижимость"),
    Industry(id = 4, name = "Медицина и фармацевтика"),
    Industry(id = 5, name = "Образование"),
)

@Preview(name = "Loading Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun IndustrySelectionLoadingPreview() {
    AppTheme {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Loading,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(
    name = "Loading Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun IndustrySelectionLoadingDarkPreview() {
    AppTheme(darkTheme = true) {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Loading,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(name = "Default Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun IndustrySelectionDefaultPreview() {
    AppTheme {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Content(
                industries = previewIndustries,
                filtered = previewIndustries,
            ),
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(name = "Search & Selection Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun IndustrySelectionSearchAndSelectionPreview() {
    val query = "фин"
    AppTheme {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Content(
                industries = previewIndustries,
                filtered = previewIndustries.filter { it.name.contains(query, ignoreCase = true) },
                searchQuery = query,
                selectedIndustry = previewIndustries[1],
            ),
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(
    name = "Default Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun IndustrySelectionDefaultDarkPreview() {
    AppTheme(darkTheme = true) {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Content(
                industries = previewIndustries,
                filtered = previewIndustries,
            ),
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(
    name = "Search & Selection Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun IndustrySelectionSearchAndSelectionDarkPreview() {
    val query = "фин"
    AppTheme(darkTheme = true) {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.Content(
                industries = previewIndustries,
                filtered = previewIndustries.filter { it.name.contains(query, ignoreCase = true) },
                searchQuery = query,
                selectedIndustry = previewIndustries[1],
            ),
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(name = "Server Error Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun IndustrySelectionServerErrorPreview() {
    AppTheme {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.ServerError,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(
    name = "Server Error Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun IndustrySelectionServerErrorDarkPreview() {
    AppTheme(darkTheme = true) {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.ServerError,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(name = "Network Error Light", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun IndustrySelectionNetworkErrorPreview() {
    AppTheme {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.NetworkError,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}

@Preview(
    name = "Network Error Dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun IndustrySelectionNetworkErrorDarkPreview() {
    AppTheme(darkTheme = true) {
        IndustrySelectionScreen(
            state = IndustrySelectionUiState.NetworkError,
            onBackClicked = {},
            onSearchQueryChanged = {},
            onIndustryClicked = {},
            onSelectClicked = {},
        )
    }
}
