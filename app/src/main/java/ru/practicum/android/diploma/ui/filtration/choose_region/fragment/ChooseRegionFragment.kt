package ru.practicum.android.diploma.ui.filtration.choose_region.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.ui.filtration.choose_region.screen.ChooseRegionScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class ChooseRegionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    ChooseRegionScreen()
                }
            }
        }
    }
}
