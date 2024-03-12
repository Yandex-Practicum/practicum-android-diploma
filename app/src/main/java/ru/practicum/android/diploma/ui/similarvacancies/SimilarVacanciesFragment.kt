package ru.practicum.android.diploma.ui.similarvacancies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarVacanciesBinding
import ru.practicum.android.diploma.presentation.search.PagingSearchAdapter
import ru.practicum.android.diploma.ui.search.SearchState
import ru.practicum.android.diploma.ui.similarvacancies.viewmodel.SimilarViewModel
import ru.practicum.android.diploma.util.extensions.visibleOrGone

class SimilarVacanciesFragment : Fragment() {

    private val viewModel: SimilarViewModel by viewModel()

    private var _binding: FragmentSimilarVacanciesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PagingSearchAdapter

    private var searchJob: Job? = null
    private var convertedVacancyId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertedVacancyId = requireArguments().getString(VACANCYID)!!

        viewModel.onSearch(convertedVacancyId)
        setupMainRecycler()

        binding.navigationToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().collect { state ->
                    binding.progressBar.visibleOrGone(state.state is SearchState.Loading)
                    binding.similarVacanciesRecyclerView.visibleOrGone(
                        state.state is SearchState.Content || state.state is SearchState.Loading)
                    binding.placeholderError.visibleOrGone(state.state is SearchState.Empty)
                    binding.placeholderNoConnection.visibleOrGone(state.state is SearchState.Error)

                    if (state.state is SearchState.Content && searchJob == null) {
                        searchJob = launch {
                            viewModel.flow.collect {
                                adapter.submitData(it)
                            }
                        }
                    } else {
                        searchJob?.cancel()
                        searchJob = null
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flow.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMainRecycler() {
        adapter = PagingSearchAdapter {
            findNavController().navigate(
                R.id.action_similarVacanciesFragment_to_vacanciesFragment,
                bundleOf("vacancy_id" to it))
        }
        binding.similarVacanciesRecyclerView.adapter = adapter
        binding.similarVacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        private const val VACANCYID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle =
            bundleOf(
                VACANCYID to vacancyId
            )
    }
}
