package ru.practicum.android.diploma.presentation.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingsFiltersBinding
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.ui.root.BottomNavigationVisibilityListener

class SettingsFilterFragment : Fragment() {
    private val viewModel by viewModel<FilterViewModel>()

    private var _binding: FragmentSettingsFiltersBinding? = null
    private val binding get() = _binding!!
    private var inputText: String = ""
    private var simpleTextWatcher: TextWatcher? = null

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationVisibilityListener) {
            bottomNavigationVisibilityListener = context
        } else {
            throw RuntimeException("$context must implement BottomNavigationVisibilityListener")
        }
    }

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

        bottomNavigationVisibilityListener?.setBottomNavigationVisibility(false)
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
                checkFieldsForResetVisibility()
                if (binding.salaryEt.text.isNotEmpty()) {
                    binding.clearButtonIcon.isVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        simpleTextWatcher?.let { binding.salaryEt.addTextChangedListener(it) }
        binding.clearButtonIcon.setOnClickListener {
            binding.salaryEt.setText("")
            binding.clearButtonIcon.isVisible = false

        }

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
            resetFields()
            viewModel.clearFilters()
        }

        binding.doNotShowWithoutSalaryCheckBox.setOnClickListener {
            viewModel.setSalaryStatus(binding.doNotShowWithoutSalaryCheckBox.isChecked)
        }
    }

    private fun changeEnabled(isEnabled: Boolean) {
        binding.confirmButton.isEnabled = isEnabled
        binding.resetSettingsTextview.isVisible = isEnabled
    }

    private fun showFilters(filters: Filters) {
        val countryName = filters.country?.name ?: ""
        val areaName = filters.area?.name ?: ""
        if (areaName.isNotEmpty()) binding.workPlaceEt.setText(buildString {
            append(countryName)
            append(", ")
            append(areaName)
        }) else binding.workPlaceEt.setText(countryName)
        binding.industryTextInputEditText.setText(filters.industry?.name ?: "")
        binding.clearButtonIcon.isVisible = !filters.preferSalary.isNullOrEmpty()
        binding.salaryEt.setText(filters.preferSalary)
        binding.doNotShowWithoutSalaryCheckBox.isChecked = filters.isIncludeSalary
    }

    private fun resetFields() {
        binding.workPlaceEt.text = null
        binding.industryTextInputEditText.text = null
        binding.salaryEt.text = null
        binding.doNotShowWithoutSalaryCheckBox.isChecked = false
        binding.clearButtonIcon.isVisible = false
        checkFieldsForResetVisibility()
    }

    private fun checkFieldsForResetVisibility() {
        val isAnyFieldNotEmpty = binding.workPlaceEt.text?.isNotEmpty() ?: false ||
                binding.industryTextInputEditText.text?.isNotEmpty() ?: false ||
                binding.salaryEt.text?.isNotEmpty() ?: false ||
                binding.doNotShowWithoutSalaryCheckBox.isChecked
        binding.resetSettingsTextview.isVisible = isAnyFieldNotEmpty
        if (!isAnyFieldNotEmpty) {
            binding.resetSettingsTextview.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}