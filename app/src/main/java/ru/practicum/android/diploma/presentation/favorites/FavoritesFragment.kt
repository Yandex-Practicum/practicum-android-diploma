package ru.practicum.android.diploma.presentation.favorites

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.ui.components.EmptyState
import ru.practicum.android.diploma.presentation.ui.components.vacancy.VacancyList
import ru.practicum.android.diploma.presentation.ui.components.vacancy.VacancyUiModel
import ru.practicum.android.diploma.presentation.ui.components.vacancy.formatSalary
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme

private const val SALARY_NOT_SPECIFIED = "Уровень зарплаты не указан"

class FavoritesFragment : Fragment() {

    private val viewModel by viewModel<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                AppTheme {
                    val state by viewModel.state.collectAsState()

                    FavoritesScreen(
                        state = state,
                        onVacancyClicked = ::onVacancyClicked,
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
            bundle,
        )
    }
}

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onVacancyClicked: (String) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            FavoritesTopBar()
        },
    ) { padding ->
        when (state) {
            FavoritesUiState.Empty -> {
                EmptyState(
                    modifier = Modifier.padding(padding),
                    imageRes = R.drawable.il_favorites_empty_328,
                    title = stringResource(R.string.favorites_empty),
                )
            }

            FavoritesUiState.Error -> {
                EmptyState(
                    modifier = Modifier.padding(padding),
                    imageRes = R.drawable.il_main_no_results_328,
                    title = stringResource(R.string.favorites_error),
                )
            }

            is FavoritesUiState.Content -> {
                VacancyList(
                    vacancies = state.vacancies.map(Vacancy::toUiModel),
                    onVacancyClick = onVacancyClicked,
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = padding.calculateTopPadding() + 32.dp,
                        bottom = padding.calculateBottomPadding(),
                    ),
                )
            }
        }
    }
}

@Composable
private fun FavoritesTopBar() {
    Text(
        text = stringResource(R.string.favorites_title),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 12.dp,
            ),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 26.sp,
        ),
    )
}

private fun Vacancy.toUiModel(): VacancyUiModel {
    return VacancyUiModel(
        id = id,
        title = city
            ?.takeIf(String::isNotBlank)
            ?.let { "$name, $it" }
            ?: name,
        company = company,
        salary = salary?.formatSalary() ?: SALARY_NOT_SPECIFIED,
        logo = logo,
    )
}

@Preview(name = "Favorites Empty", showBackground = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    AppTheme {
        FavoritesScreen(
            state = FavoritesUiState.Empty,
            onVacancyClicked = {},
        )
    }
}

@Preview(name = "Favorites Error Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenErrorPreview() {
    AppTheme(darkTheme = true) {
        FavoritesScreen(
            state = FavoritesUiState.Error,
            onVacancyClicked = {},
        )
    }
}
