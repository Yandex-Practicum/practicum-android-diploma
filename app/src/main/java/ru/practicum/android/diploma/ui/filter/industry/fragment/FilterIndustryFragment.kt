package ru.practicum.android.diploma.ui.filter.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.util.FilterNames
import ru.practicum.android.diploma.util.coroutine.CoroutineUtils

class FilterIndustryFragment : Fragment() {

    private companion object {
        const val DELAY = 1000L
    }

    private enum class PlaceholderState {
        ERROR, LOAD
    }

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterIndustryViewModel by viewModel()
    private var adapter: IndustryAdapter? = null
    private var selectedIndustry: Industry? = null

    private var selectedIndustryId: String? = null
    private var selectedIndustryName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            selectedIndustryId = it.getString(FilterNames.INDUSTRY_ID)
            selectedIndustryName = it.getString(FilterNames.INDUSTRY_NAME)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupSearch()

        adapter = IndustryAdapter { thisIndustry ->
            adapter?.selectIndustry(thisIndustry)
            selectedIndustry = thisIndustry
            binding.buttonSelect.isVisible = true
        }

        binding.industryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FilterIndustryFragment.adapter
        }

        binding.buttonSelect.setOnClickListener {
            sendSelectedIndustry()
        }

        binding.clearIcon.setOnClickListener {
            clearText()
        }

        binding.errorPlaceholderIndustry.isVisible = false
        binding.progressBarIndustry.isVisible = true

        observeIndustries()
        viewModel.loadIndustries()
    }

    private fun observeIndustries() {
        viewModel.industries.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    renderList(PlaceholderState.LOAD)
                    resource.value?.let {
                        adapter?.industriesFull = resource.value
                    }
                    adapter?.submitList(resource.value?.sortedBy { it.name })

                    selectedIndustryName?.let { name ->
                        binding.editTextSearch.setText(name)
                        adapter?.filter(name) {
                            updatePlaceholder(it)
                            selectedIndustryId?.let { id ->
                                adapter?.selectIndustry(Industry(id, name))
                            }
                        }
                    }
                }

                is Resource.Error -> renderList(PlaceholderState.ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        CoroutineUtils.debounceJob?.cancel()
    }

    private fun renderList(state: PlaceholderState) {
        when (state) {
            PlaceholderState.ERROR -> {
                binding.progressBarIndustry.isVisible = false
                binding.errorPlaceholderIndustry.isVisible = true
                binding.industryRecyclerView.isVisible = false
                binding.buttonSelect.isVisible = false
            }

            PlaceholderState.LOAD -> {
                binding.progressBarIndustry.isVisible = false
                binding.errorPlaceholderIndustry.isVisible = false
                binding.industryRecyclerView.isVisible = true
                binding.buttonSelect.isVisible = false
            }
        }
    }

    private fun sendSelectedIndustry() {
        selectedIndustry?.let { industry ->
            val result = Bundle().apply {
                putString(FilterNames.INDUSTRY_ID, industry.id)
                putString(FilterNames.INDUSTRY_NAME, industry.name)
            }
            parentFragmentManager.setFragmentResult(FilterNames.INDUSTRY_RESULT, result)

            findNavController().popBackStack()
        }
    }

    private fun clearText() {
        binding.editTextSearch.setText("")
        binding.editTextSearch.clearFocus()
        binding.errorPlaceholderIndustry.isVisible = false
        binding.industryRecyclerView.isVisible = true
        selectedIndustryId = null
        selectedIndustryName = null

        adapter?.selectedIndustryId = null
        adapter?.submitList(adapter?.industriesFull?.sortedBy { it.name }?.toList())
        binding.buttonSelect.isVisible = false
    }

    private fun setupSearch() {
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            binding.clearIcon.isVisible = !text.isNullOrEmpty()
            binding.searchIcon.isVisible = text.isNullOrEmpty()
            binding.buttonSelect.isVisible = false

            CoroutineUtils.debounce(viewLifecycleOwner.lifecycleScope, DELAY) {
                val query = text?.toString()?.trim().orEmpty()
                adapter?.filter(query) {
                    updatePlaceholder(it)
                }
            }
        }
    }

    private fun updatePlaceholder(itemCount: Int) {
        binding.errorPlaceholderIndustry.isVisible = itemCount == 0
        binding.industryRecyclerView.isVisible = itemCount > 0
    }
}
