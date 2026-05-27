package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.SharedFlow
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySalary
import ru.practicum.android.diploma.presentation.ui.components.vacancy.VacancyList
import ru.practicum.android.diploma.presentation.ui.components.vacancy.VacancyUiModel
import ru.practicum.android.diploma.presentation.ui.components.vacancy.formatSalary
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.WhiteUniversal

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()

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

                    SearchScreen(
                        state = state,
                        onQueryChanged = viewModel::onQueryChanged,
                        onClearQueryClicked = viewModel::onClearQueryClicked,
                        onVacancyClicked = ::onVacancyClicked,
                        onLoadNextPage = viewModel::loadNextPage,
                        paginationError = viewModel.paginationError,
                    )
                }
            }
        }
    }

    private fun onVacancyClicked(vacancyId: String) {
        val bundle = Bundle().apply {
            putString("vacancyId", vacancyId)
        }
        findNavController().navigate(
            R.id.vacancyDetailsFragment,
            bundle
        )
    }

    companion object {
        const val VACANCY_CLICK_REQUEST_KEY = "vacancy_click_request"
        const val VACANCY_ID_KEY = "vacancy_id"
    }
}

@Composable
private fun SearchScreen(
    state: SearchUiState,
    onQueryChanged: (String) -> Unit,
    onClearQueryClicked: () -> Unit,
    onVacancyClicked: (String) -> Unit,
    onLoadNextPage: () -> Unit = {},
    paginationError: SharedFlow<SearchError>? = null,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
            .padding(horizontal = Dimens.paddingDefault),
    ) {
        SearchTopBar()
        Spacer(modifier = Modifier.height(Dimens.paddingSystemBar))
        SearchField(
            query = state.query,
            onQueryChanged = onQueryChanged,
            onClearQueryClicked = onClearQueryClicked,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            when {
                state.isLoading -> SearchLoading()
                state.vacancies.isNotEmpty() -> SearchResults(
                    state = state,
                    onScroll = { focusManager.clearFocus() },
                    onVacancyClicked = onVacancyClicked,
                    onLoadNextPage = onLoadNextPage,
                    paginationError = paginationError,
                )
                state.errorType == SearchError.NO_INTERNET -> NoInternetPlaceholder()
                state.errorType == SearchError.SERVER_ERROR -> ServerErrorPlaceholder()
                state.errorType == SearchError.EMPTY -> Column(Modifier.fillMaxSize()) {
                    SearchResultCounter(text = stringResource(R.string.search_results_empty))
                    EmptyResultsPlaceholder(modifier = Modifier.weight(1f).fillMaxWidth())
                }
                state.query.isBlank() -> SearchInitialPlaceholder()
            }
        }
    }
}

@Composable
private fun SearchTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.paddingTopLarge),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.search_title),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(R.drawable.ic_filter_off_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQueryClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.searchFieldHeight)
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = Dimens.paddingDefault, end = Dimens.paddingSystemBar),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            cursorBrush = SolidColor(Blue),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (query.isBlank()) {
                    Text(
                        text = stringResource(R.string.search_hint),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                    )
                }
                innerTextField()
            },
        )

        if (query.isBlank()) {
            Box(
                modifier = Modifier.size(Dimens.iconSizeMedium),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        } else {
            IconButton(onClick = onClearQueryClicked) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun SearchInitialPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.il_main_background_328),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.searchIllustrationBottomPadding),
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Composable
private fun NoInternetPlaceholder() {
    SearchPlaceholder(
        imageRes = R.drawable.il_main_no_internet_328,
        textRes = R.string.search_error_no_internet,
    )
}

@Composable
private fun ServerErrorPlaceholder() {
    SearchPlaceholder(
        imageRes = R.drawable.il_main_error_server_328,
        textRes = R.string.search_error_server,
    )
}

@Composable
private fun EmptyResultsPlaceholder(modifier: Modifier = Modifier.fillMaxSize()) {
    SearchPlaceholder(
        imageRes = R.drawable.il_main_no_results_328,
        textRes = R.string.search_no_results,
        modifier = modifier,
    )
}

@Composable
private fun SearchPlaceholder(imageRes: Int, textRes: Int, modifier: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Spacer(modifier = Modifier.height(Dimens.paddingDefault))
            Text(
                text = stringResource(textRes),
                modifier = Modifier.padding(horizontal = Dimens.paddingExtraLarge),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
            )
        }
    }
}

@Composable
private fun SearchLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.searchProgressSize),
            color = Blue,
            strokeWidth = Dimens.paddingSmall,
        )
    }
}

@Composable
private fun SearchResults(
    state: SearchUiState,
    onScroll: () -> Unit,
    onVacancyClicked: (String) -> Unit,
    onLoadNextPage: () -> Unit = {},
    paginationError: SharedFlow<SearchError>? = null,
) {
    val listState = rememberLazyListState()

    val reachedEnd by remember(listState, state.vacancies.size) {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= state.vacancies.lastIndex
        }
    }

    LaunchedEffect(reachedEnd) {
        if (reachedEnd) onLoadNextPage()
    }

    val context = LocalContext.current
    val resources = context.resources
    LaunchedEffect(paginationError) {
        paginationError?.collect { error ->
            val message = when (error) {
                SearchError.NO_INTERNET -> resources.getString(R.string.search_toast_no_internet)
                else -> resources.getString(R.string.search_toast_server_error)
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val keyboardDismissConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            onScroll()
            return Offset.Zero
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VacancyList(
            vacancies = state.vacancies.map(Vacancy::toUiModel),
            onVacancyClick = onVacancyClicked,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(keyboardDismissConnection),
            listState = listState,
            contentPadding = PaddingValues(
                top = Dimens.searchResultCounterTopPadding +
                    Dimens.heightVacancyPanel +
                    Dimens.searchResultCounterBottomPadding,
                bottom = Dimens.paddingDefault,
            ),
            isNextPageLoading = state.isNextPageLoading,
        )

        SearchResultCounter(text = pluralStringResource(R.plurals.search_results_count, state.found, state.found))
    }
}

@Composable
private fun SearchResultCounter(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.searchResultCounterTopPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clip(CircleShape)
                .background(Blue)
                .padding(
                    horizontal = Dimens.paddingMedium,
                    vertical = Dimens.searchResultCounterVerticalPadding,
                ),
            color = WhiteUniversal,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp
            ),
        )
    }
}

private fun Vacancy.toUiModel(): VacancyUiModel {
    return VacancyUiModel(
        id = id,
        title = nameWithCity(),
        company = company,
        salary = salary.formatSalary(),
        logo = logo,
    )
}

private fun Vacancy.nameWithCity(): String {
    return city
        ?.takeIf(String::isNotBlank)
        ?.let { "$name, $it" }
        ?: name
}

@Preview(
    name = "Search initial",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun SearchInitialPreview() {
    AppTheme {
        SearchScreen(
            state = SearchUiState(),
            onQueryChanged = {},
            onClearQueryClicked = {},
            onVacancyClicked = {},
        )
    }
}

@Preview(
    name = "Search loading",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun SearchLoadingPreview() {
    AppTheme {
        SearchScreen(
            state = SearchUiState(
                query = "Разработчик",
                isLoading = true,
            ),
            onQueryChanged = {},
            onClearQueryClicked = {},
            onVacancyClicked = {},
        )
    }
}

@Preview(
    name = "Search results",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun SearchResultsPreview() {
    AppTheme {
        SearchScreen(
            state = SearchUiState(
                query = "Разработчик",
                found = 286,
                vacancies = previewVacancies,
            ),
            onQueryChanged = {},
            onClearQueryClicked = {},
            onVacancyClicked = {},
        )
    }
}

private val previewVacancies = listOf(
    Vacancy(
        id = "1",
        name = "Android-разработчик",
        company = "Еда",
        city = "Москва",
        salary = VacancySalary(from = 100_000, to = null, currency = "RUR"),
        logo = null,
    ),
    Vacancy(
        id = "2",
        name = "Разработчик платформы данных",
        company = "Алиса",
        city = "Санкт-Петербург",
        salary = VacancySalary(from = 1_500, to = null, currency = "USD"),
        logo = null,
    ),
    Vacancy(
        id = "3",
        name = "Java-разработчик",
        company = "Маркет",
        city = "Омск",
        salary = null,
        logo = null,
    ),
    Vacancy(
        id = "4",
        name = "Разработчик на C++ в команду внутренних сервисов",
        company = "Авто.ру",
        city = "Москва",
        salary = VacancySalary(from = 1_000, to = 1_500, currency = "EUR"),
        logo = null,
    ),
)
