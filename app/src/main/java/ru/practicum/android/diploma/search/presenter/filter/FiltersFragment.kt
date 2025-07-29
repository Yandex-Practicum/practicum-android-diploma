package ru.practicum.android.diploma.search.presenter.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.search.domain.model.Industry

class FiltersFragment : Fragment() {
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltersViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupListeners()
        setupTextWatchers()
    }

    private fun observeViewModel() {
        viewModel.selectedIndustry.onEach { industry ->
            updateIndustryField(industry)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.expectedSalary.onEach { salary ->
            if (binding.editTextId.text.toString() != (salary ?: "")) {
                binding.editTextId.setText(salary)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.noSalaryOnly.onEach { isChecked ->
            if (binding.noSalaryCheckbox.isChecked != isChecked) {
                binding.noSalaryCheckbox.isChecked = isChecked
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.hasAnyActiveFilters.onEach { hasFilters ->
            binding.resetButton.visibility = if (hasFilters) View.VISIBLE else View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.areFiltersChanged.onEach { areChanged ->
            binding.applyButton.visibility = if (areChanged) View.VISIBLE else View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupListeners() {
        binding.backButtonId.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.applyButton.setOnClickListener {
            viewModel.applyFilters()
            setFragmentResult(
                getString(R.string.filter_request),
                bundleOf(getString(R.string.filters_applied) to true)
            )
            findNavController().popBackStack()
        }

        binding.resetButton.setOnClickListener {
            viewModel.clearFilters()
        }

        binding.fieldId.setOnClickListener {
            findNavController().navigate(R.id.action_filtersFragment_to_fieldsFragment)
        }
    }

    private fun setupTextWatchers() {
        binding.editTextId.doAfterTextChanged { text ->
            viewModel.updateSalary(text.toString())
        }

        binding.noSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateNoSalaryOnly(isChecked)
        }
    }

    private fun updateIndustryField(industry: Industry?) {
        if (industry != null) {
            binding.filterField.text = industry.name
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        } else {
            binding.filterField.text = getString(R.string.filter_field)
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
