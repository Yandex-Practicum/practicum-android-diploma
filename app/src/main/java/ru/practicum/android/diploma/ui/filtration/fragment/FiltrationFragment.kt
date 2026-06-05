package ru.practicum.android.diploma.ui.filtration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.ui.filtration.screen.FiltrationScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class FiltrationFragment : Fragment() {
    private val viewModel: FiltrationViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        viewModel.loadFilters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val state = viewModel.state.collectAsStateWithLifecycle()

                    FiltrationScreen(
                        country = "",
                        industryName = state.value.industry?.name,
                        salary = state.value.salary?.toString().orEmpty(),
                        dontShowWithoutSalaryChecked = state.value.onlyWithSalary,
                        showButtons = state.value.showButtons,
                        onCheckedChange = { viewModel.onOnlyWithSalaryChanged(it) },
                        onSearchTextChange = { viewModel.onSalaryChanged(it) },
                        onClear = { viewModel.onSalaryCleared() },
                        onApplyClick = {
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.saveFilters()
                                findNavController().popBackStack()
                            }
                        },
                        onCancelClick = { viewModel.resetFilters() },
                        onNavClick = { findNavController().popBackStack() },
                        onIndustryClick = {
                            findNavController().navigate(
                                R.id.action_filtrationFragment_to_chooseIndustryFragment,
                            )
                        },
                        onAreaClick = {
                            findNavController().navigate(
                                R.id.action_filtrationFragment_to_placeOfWorkFragment,
                            )
                        },
                        onIndustryClear = { viewModel.clearIndustry() },
                        onAreaClear = {},
                    )
                }
            }
        }
    }
}
