package ru.practicum.android.diploma.ui.filtration.industry.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryUiState
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.TextEdit
import ru.practicum.android.diploma.ui.common.TextEditTrailingIcon
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.common.industry.IndustryItem
import ru.practicum.android.diploma.ui.search.screen.SearchScreenTestTags
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun ChooseIndustryScreen(
    state: IndustryUiState,
    searchQuery: String,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onItemClick: () -> Unit,
    onNavClick: () -> Unit,

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.industry_choice_title),
                navIconVisible = true,
                endFirstIconVisible = false,
                endSecondIconVisible = false,
                onNavClick = onNavClick,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            IndustryTextEdit(
                searchQuery = searchQuery,
                interactionSource = interactionSource,
                onSearchTextChange = onSearchTextChange,
                onClear = onClear,
                onKeyboardDone = { keyboardController?.hide() },
            )
            Box(modifier = Modifier.weight(1F)) {
                IndustrySearchStateContent(
                    state = state,
                    onClick = onItemClick,
                )
            }
        }
    }
}

@Composable
fun IndustryTextEdit(
    searchQuery: String,
    interactionSource: MutableInteractionSource,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onKeyboardDone: () -> Unit,
    ) {
        val fieldShape = RoundedCornerShape(8.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.ScreenHorizontalPadding,
                    top = 8.dp,
                    end = Dimens.ScreenHorizontalPadding,
                )
                .height(56.dp)
                .clip(fieldShape)
                .background(MaterialTheme.colorScheme.surfaceContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextEdit(
                value = searchQuery,
                onValueChange = onSearchTextChange,
                modifier = Modifier
                    .weight(1f)
                    .testTag(SearchScreenTestTags.TextField)
                    .fillMaxHeight()
                    .padding(start = 20.dp),
                interactionSource = interactionSource,
                onKeyboardDone = onKeyboardDone,
                decorationBox = { innerTextField ->
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = stringResource(R.string.region_search_placeholder_text),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.inverseOnSurface
                                ),
                                maxLines = 1,
                                modifier = Modifier.align(Alignment.CenterStart),
                            )
                        }
                        innerTextField()
                    }
                },
            )
            TextEditTrailingIcon(
                if (searchQuery.isEmpty()) R.drawable.ic_search else R.drawable.ic_cross,
                onClear
            )
        }
}

@Composable
private fun IndustrySearchStateContent(
    state: IndustryUiState,
    onClick: () -> Unit,
) {
    when (state) {
        is IndustryUiState.Content -> IndustriesContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.ScreenHorizontalPadding,
                    top = 8.dp,
                    end = Dimens.ScreenHorizontalPadding,
                ),
            industries = state.industries,
            isLoading = state.isLoading,
            onClick = onClick
        )

        IndustryUiState.Initial -> {
            //N0 content
        }

        IndustryUiState.Error -> {
            PlaceholderLayout(
                R.drawable.img_industries_error,
                R.string.industry_server_error
            )
        }
    }
}

@Composable
fun IndustriesContent(
    modifier: Modifier = Modifier,
    industries: List<FilterIndustryDto>, // Industry
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Box(contentAlignment = Alignment.TopCenter) {
            IndustryList(
                industries = industries,
                isLoading = isLoading,
                onClick = onClick,
            )
        }
        if (isLoading && industries.isEmpty()) {
            Loader(
                modifier
                    .weight(1F)
                    .imePadding()
            )
        }
    }

}

@Composable
fun IndustryList(
    modifier: Modifier = Modifier,
    industries: List<FilterIndustryDto>,// Industry
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        items(
            items = industries,
            key = { it.id }
        ) { industry ->
            IndustryItem(
                text = industry.name,
                checked = false,
                onItemClick = onClick
            )
        }
        item {
            if (isLoading && industries.isNotEmpty()) {
                Loader(
                    modifier = modifier
                        .heightIn(min = 80.dp)
                )
            }
        }
    }
}

