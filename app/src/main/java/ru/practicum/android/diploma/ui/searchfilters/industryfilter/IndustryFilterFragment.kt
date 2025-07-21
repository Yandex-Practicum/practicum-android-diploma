package ru.practicum.android.diploma.ui.searchfilters.industryfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.IndustriesFilterFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.presentation.searchfilters.industries.IndustriesFilterViewModel
import ru.practicum.android.diploma.presentation.searchfilters.industries.IndustriesUiState
import ru.practicum.android.diploma.ui.searchfilters.industryfilter.recyclerview.IndustryItemAdapter
import ru.practicum.android.diploma.util.hideKeyboardOnDone

class IndustryFilterFragment : Fragment(), IndustryItemAdapter.OnClickListener {
    private var _binding: IndustriesFilterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<IndustriesFilterViewModel>()
    private val adapter = IndustryItemAdapter(this)

    private var selectedIndustry: Industry? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IndustriesFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter

        observeViewModels()
        setupSearchInput()

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnChoose.setOnClickListener {
            selectedIndustry?.let {
                viewModel.onClickIndustry(it)
            }
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
                    binding.industryPlaceholder.isVisible = false
                }

                is IndustriesUiState.Error -> {
                    binding.recyclerViewSearch.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.industryPlaceholder.isVisible = true
                    binding.industryCoverPlaceholder.setImageResource(R.drawable.unable_obtain_list_placeholder)
                    binding.industryTextPlaceholder.setText(R.string.unable_obtain_list)
                }

                is IndustriesUiState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerViewSearch.isVisible = false
                    binding.industryPlaceholder.isVisible = false
                }

                is IndustriesUiState.Empty -> {
                    binding.recyclerViewSearch.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.industryPlaceholder.isVisible = true
                }
            }
        }
    }

    private fun setupSearchInput() {
        binding.inputEditText.doOnTextChanged { text, start, before, count ->
            val query = text?.toString()?.trim().orEmpty()

            if (query.isNotEmpty() && binding.inputEditText.hasFocus()) {
                binding.icon.setImageResource(R.drawable.close_24px)
                binding.icon.setOnClickListener {
                    binding.inputEditText.setText("")
                }
            } else {
                binding.icon.setImageResource(R.drawable.search_24px)
            }
            viewModel.search(query)
        }

        binding.inputEditText.hideKeyboardOnDone(requireContext())
    }

    override fun onClick(industry: Industry) {
        selectedIndustry = industry
        binding.btnChoose.isVisible = true
        adapter.setSelectedId(industry.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
