package ru.practicum.android.diploma.ui.filtration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.ui.filtration.screen.FiltrationScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class FiltrationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    FiltrationScreen(
                        specialization = "Разработчик",
                        expectedSalary = "100000",
                        country = "Россия",
                        dontShowWithoutSalaryChecked = true,
                        onCheckedChange = {},
                        onSearchTextChange = {},
                        onClear = {}
                    )
                }
            }
        }
    }
}
