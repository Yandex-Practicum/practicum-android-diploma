package ru.practicum.android.diploma.ui.filtration.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryUiState
import ru.practicum.android.diploma.presentation.filtration.industry.viewmodel.ChooseIndustryViewModel
import ru.practicum.android.diploma.ui.filtration.industry.screen.ChooseIndustryScreen
import ru.practicum.android.diploma.ui.theme.AppTheme
import kotlin.getValue

class ChooseIndustryFragment : Fragment() {

    private val viewModel: ChooseIndustryViewModel by viewModel()

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

                    ChooseIndustryScreen(
                        state = IndustryUiState.Error,
                        searchQuery = "",
                        showButton = false,
                        onSearchTextChange = {},
                        onClear = {},
                        onItemClick = {},
                        onChooseButtonClick = {},
                        onNavClick = {},
                    )
                }
            }
        }
    }
}
