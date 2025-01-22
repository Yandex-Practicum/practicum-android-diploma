package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterSettingsFragment)
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
          // render(it)
        }
        viewModel.searchVacancy("Программист")
    }
    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Success -> TODO()
            is SearchViewState.Loading -> TODO()
            SearchViewState.ConnectionError -> TODO()
            SearchViewState.NotFoundError -> TODO()
            SearchViewState.ServerError -> TODO()
            else -> {}
        }
    }

}
