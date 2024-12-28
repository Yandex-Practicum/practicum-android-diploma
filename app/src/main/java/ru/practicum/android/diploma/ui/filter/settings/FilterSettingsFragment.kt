package ru.practicum.android.diploma.ui.filter.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.domain.models.Filter

class FilterSettingsFragment : Fragment() {

    private var _binding: FragmentFilterSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterSettingsViewModel>()
    private var filterSave: Filter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterSettingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentFilter()
        viewModel.counterFilter.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.btApply.setOnClickListener {
            applyFilter()
        }

        binding.btReset.setOnClickListener {
            viewModel.clearFilters()
        }

        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_choiceWorkplaceFragment)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etIndustries.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_choiceIndustryFragment)
        }
    }

    private fun applyFilter() {
        val checkBox = binding.checkBoxSalary.isChecked

        if (binding.etSalary.text.isNullOrEmpty()) {
            filterSave = Filter(onlyWithSalary = checkBox)
        } else {
            val salary = binding.etSalary.text.toString().toInt()
            filterSave = Filter(
                salary = salary,
                onlyWithSalary = checkBox
            )
        }
        viewModel.saveFilterFromUi(filterSave!!)
        findNavController().popBackStack()
    }

    private fun renderState(state: FilterSettingsState) {
        when (state) {
            is FilterSettingsState.FilterSettings -> {
                setFilteredUi(state.filter)
                binding.btApply.isVisible = true
                binding.btReset.isVisible = true
            }

            is FilterSettingsState.Empty -> {
                clearFilter()
                binding.btReset.isVisible = false
                binding.btApply.isVisible = false
            }
        }
    }

    private fun clearFilter() {
        binding.etCountry.setText("")
        binding.etIndustries.setText("")
        binding.etSalary.setText("")
        binding.checkBoxSalary.setChecked(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFilteredUi(filter: Filter) {
        binding.etCountry.setText(filter.country?.name ?: "")
        binding.etIndustries.setText(filter.industry?.name ?: "")
        binding.etSalary.setText(if (filter.salary != null && filter.salary != 0) filter.salary.toString() else "")
        if (filter.onlyWithSalary != null) {
            binding.checkBoxSalary.setChecked(filter.onlyWithSalary!!)
        }
        filterSave = filter
    }
}
