package ru.practicum.android.diploma.ui.filtration.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryUiState
import ru.practicum.android.diploma.presentation.filtration.industry.viewmodel.ChooseIndustryViewModel
import ru.practicum.android.diploma.presentation.filtration.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.ui.filtration.industry.screen.ChooseIndustryScreen
import ru.practicum.android.diploma.ui.theme.AppTheme
import kotlin.getValue

class ChooseIndustryFragment : Fragment() {

    private val viewModel: ChooseIndustryViewModel by viewModel()
    private val filtersViewModel: FiltrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()

                    ChooseIndustryScreen(
                        state = state.value,
                        searchQuery = searchQuery.value,
                        showButton = uiState.value.showButton,
                        onSearchTextChange = { viewModel.onSearchQueryChanged(it) },
                        onClear = { viewModel.clearSearch() },
                        onItemClick = {
                            viewModel.onIndustryClick(it)
                            filtersViewModel.chooseIndustry(it)
                                      },
                        onChooseButtonClick = {
                            filtersViewModel.saveFilters()
                            findNavController().navigate(
                                R.id.action_industrySelectionFragment_to_filtrationFragment,
                            )
                                              },
                        onNavClick = { findNavController().popBackStack() },
                    )
                }
            }
        }
    }
}
