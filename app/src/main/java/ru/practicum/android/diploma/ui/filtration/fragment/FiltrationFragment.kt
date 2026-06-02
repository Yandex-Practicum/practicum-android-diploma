package ru.practicum.android.diploma.ui.filtration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.filtration.action.FiltrationAction
import ru.practicum.android.diploma.presentation.filtration.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.ui.filtration.screen.FiltrationScreen
import ru.practicum.android.diploma.ui.theme.AppTheme
import kotlin.getValue

class FiltrationFragment : Fragment() {
    private val viewModel: FiltrationViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state = viewModel.state.collectAsStateWithLifecycle()

                AppTheme {
                    FiltrationScreen(
                        industry = state.value.industry?.name.orEmpty(),
                        salary = state.value.salary?.toString().orEmpty(),
                        country = "",
                        dontShowWithoutSalaryChecked = state.value.onlyWithSalary,
                        onCheckedChange = { checked ->
                            viewModel.onAction(FiltrationAction.OnlyWithSalaryChanged(checked))
                        },
                        onSearchTextChange = { text ->
                            val digits = text.filter { it.isDigit() }
                            when {
                                digits.isEmpty() -> viewModel.onAction(FiltrationAction.SalaryCleared)
                                else -> digits.toIntOrNull()?.let {
                                    viewModel.onAction(FiltrationAction.SalaryChanged(it))
                                }
                            }
                        },
                        onClear = { viewModel.onAction(FiltrationAction.SalaryCleared) },
                        onApplyClick = { viewModel.onAction(FiltrationAction.ApplyClicked) },
                        onCancelClick = { viewModel.onAction(FiltrationAction.ResetClicked) },
                        onNavClick = { viewModel.onAction(FiltrationAction.BackClicked) },
                        onIndustryClick = { viewModel.onAction(FiltrationAction.IndustryClicked) },
                        onAreaClick = {},
                        onIndustryClear = { viewModel.onAction(FiltrationAction.IndustryCleared) },
                        onAreaClear = {}
                    )
                }
            }
        }
    }
}
