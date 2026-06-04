package ru.practicum.android.diploma.ui.filtration.region.fragment

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
import ru.practicum.android.diploma.presentation.filtration.region.viewmodel.ChooseRegionViewModel
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.filtration.region.screen.ChooseRegionScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class ChooseRegionFragment : Fragment() {

    private val viewModel: ChooseRegionViewModel by viewModel()

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

                    ChooseRegionScreen(
                        state = state.value,
                        onAction = ::handleAction,
                    )
                }
            }
        }
    }

    private fun handleAction(action: ChooseRegionAction) {
        when (action) {
            ChooseRegionAction.BackClicked -> findNavController().navigateUp()

            is ChooseRegionAction.SearchQueryChanged -> {
                viewModel.onSearchQueryChanged(action.query)
            }

            ChooseRegionAction.ClearSearchClicked -> {
                viewModel.onClearSearchClicked()
            }

            is ChooseRegionAction.RegionClicked -> {
                setRegionResult(action.region)
                findNavController().navigateUp()
            }
        }
    }

    private fun setRegionResult(region: RegionUi) {
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(REGION_ID_KEY, region.id)

        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(REGION_NAME_KEY, region.name)
    }

    private companion object {
        const val REGION_ID_KEY = "regionId"
        const val REGION_NAME_KEY = "regionName"
    }
}
