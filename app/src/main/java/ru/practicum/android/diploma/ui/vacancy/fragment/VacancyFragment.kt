package ru.practicum.android.diploma.ui.vacancy.fragment

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
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.vacancy.screen.VacancyScreen

class VacancyFragment : Fragment() {
    private val viewModel: VacancyViewModel by viewModel()
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
                    VacancyScreen(
                        state = state.value,
                        onLoadVacancy = { viewModel.loadVacancy() },
                        navigateBack = { findNavController().navigateUp() }
                    )
                }
            }
        }
    }
}
