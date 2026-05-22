package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel
import ru.practicum.android.diploma.ui.search.screen.JobSearchScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class JobSearchFragment : Fragment() {

    private val viewModel by viewModel<JobSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    JobSearchScreen(
                        viewModel,
                        {
                            //TODO: onSearchTextChange
                        },
                    )
                }
            }
        }
    }
}
