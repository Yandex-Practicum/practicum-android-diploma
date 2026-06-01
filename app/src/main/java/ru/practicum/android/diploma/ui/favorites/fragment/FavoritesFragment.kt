package ru.practicum.android.diploma.ui.favorites.fragment

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
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.favorites.screen.FavoritesScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()

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
                    FavoritesScreen(
                        state = state.value,
                        onVacancyClick = { vacancyId ->
                            findNavController().navigate(
                                FavoritesFragmentDirections
                                    .actionFavoritesFragmentToVacancyFragment(vacancyId)
                            )
                        }
                    )
                }
            }
        }
    }
}
