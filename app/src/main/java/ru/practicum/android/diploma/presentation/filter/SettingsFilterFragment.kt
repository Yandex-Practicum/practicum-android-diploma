package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingsFiltersBinding
import ru.practicum.android.diploma.domain.models.filter.Filters

class SettingsFilterFragment : Fragment() {
    private val viewModel by viewModel<FilterViewModel>()

    private var _binding: FragmentSettingsFiltersBinding? = null
    private val binding get() = _binding!!
    private var inputText: String = ""
    private var simpleTextWatcher: TextWatcher? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilters()
        binding.confirmButton.isEnabled = false
        viewModel.observeChanges().observe(viewLifecycleOwner) {
            changeEnabled(it)
        }
        viewModel.observeFilters().observe(viewLifecycleOwner) {
            showFilters(it)
        }
        binding.salaryEt.isSelected = (inputText.isNotEmpty())
        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputText = s?.toString() ?: ""
                viewModel.checkChanges(inputText)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        simpleTextWatcher?.let { binding.salaryEt.addTextChangedListener(it) }

        binding.confirmButton.setOnClickListener {
            viewModel.setSalary(inputText)
            findNavController().popBackStack()
        }
        binding.settingsBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.workPlaceEt.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFiltersFragment_to_selectWorkplaceFragment)
        }

        binding.industryTextInputEditText.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFiltersFragment_to_selectIndustryFragment)
        }

        binding.resetSettingsTextview.setOnClickListener {
            viewModel.clearFilters()
        }

        binding.doNotShowWithoutSalaryCheckBox.setOnClickListener {

        }
    }

    private fun changeEnabled(isEnabled: Boolean) {
        binding.confirmButton.isEnabled = isEnabled
    }

    private fun showFilters(filters: Filters) {
        val countryName = filters.country?.name ?: ""
        val areaName = filters.area?.name ?: ""
        if (countryName.isNotEmpty()) binding.workPlaceEt.setText("$countryName, $areaName")
        binding.industryTextInputEditText.setText(filters.industry?.name ?: "")
        binding.salaryEt.setText(filters.preferSalary)
        binding.doNotShowWithoutSalaryCheckBox.isChecked = filters.isIncludeSalary
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}