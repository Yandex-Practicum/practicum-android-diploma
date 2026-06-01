package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel
import ru.practicum.android.diploma.ui.search.screen.JobSearchScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class JobSearchFragment : Fragment() {
    private val viewModel: JobSearchViewModel by viewModel()
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
                    val query = viewModel.searchQuery.collectAsStateWithLifecycle()

                    JobSearchScreen(
                        state = state.value,
                        searchQuery = query.value,
                        onSearchTextChange = { viewModel.onSearchQueryChanged(it) },
                        onVacancyClick = { vacancyId ->
                            findNavController().navigate(
                                JobSearchFragmentDirections.actionJobSearchFragmentToVacancyFragment(
                                    vacancyId
                                )
                            )
                        },
                        onClear = { viewModel.clearSearch() },
                        onLoadNextPage = { viewModel.loadNextPage() },
                        onNetworkError = { showToast(context.getString(R.string.network_error_toast)) },
                        onNavigationTap = {
                            findNavController().navigate(
                                JobSearchFragmentDirections.actionJobSearchFragmentToFiltrationFragment(

                                )
                            )
                        }
                    )
                }
            }
        }
    }

    fun showToast(message: String?) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireActivity(), message ?: "Empty message", Toast.LENGTH_LONG)
                .show()
        }
    }
}
