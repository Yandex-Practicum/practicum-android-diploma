package ru.practicum.android.diploma.ui.filtration.industry.fragment

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
import ru.practicum.android.diploma.presentation.filtration.industry.viewmodel.IndustryViewModel
import ru.practicum.android.diploma.ui.filtration.industry.screen.ChooseIndustryScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class IndustryFragment : Fragment() {
    private val viewModel: IndustryViewModel by viewModel()

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

                    ChooseIndustryScreen(
                        state = state.value,
                        onSearchTextChange = { viewModel.onSearchQueryChanged(it) },
                        onClear = { viewModel.clearSearch() },
                        onItemClick = { viewModel.onIndustryClick(it) },
                        onChooseButtonClick = {
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.saveSelectedIndustry()
                                findNavController().popBackStack()
                            }
                        },
                        onNavClick = { findNavController().popBackStack() },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadIndustry()
    }
}
