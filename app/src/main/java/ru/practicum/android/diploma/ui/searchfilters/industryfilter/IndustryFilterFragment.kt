package ru.practicum.android.diploma.ui.searchfilters.industryfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.IndustriesFilterFragmentBinding
import ru.practicum.android.diploma.domain.models.industries.Industry
import ru.practicum.android.diploma.presentation.searchfilters.industries.IndustriesFilterViewModel
import ru.practicum.android.diploma.presentation.searchfilters.industries.IndustriesUiState
import ru.practicum.android.diploma.ui.searchfilters.industryfilter.recyclerview.IndustryItemAdapter

class IndustryFilterFragment : Fragment(), IndustryItemAdapter.OnClickListener {
    private var _binding: IndustriesFilterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<IndustriesFilterViewModel>()
    private val adapter = IndustryItemAdapter(this)

    private var selectedIndustryId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IndustriesFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter

        observeViewModels()

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModels() {
        viewModel.industriesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesUiState.Content -> {
                    binding.recyclerViewSearch.isVisible = true
                    adapter.submitList(state.industries)
                    binding.progressBar.isVisible = false
                }

                is IndustriesUiState.Error -> {
                    binding.recyclerViewSearch.isVisible = false
                    binding.progressBar.isVisible = false
                }

                is IndustriesUiState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerViewSearch.isVisible = false
                }
            }
        }
    }

    override fun onClick(id: String) {
        val selectedIndustry = adapter.currentList.find { it.id == id }
        selectedIndustry?.let {
            adapter.submitList(listOf(it))
        }
        adapter.setSelectedId(id)
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
