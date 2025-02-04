package ru.practicum.android.diploma.filter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.IndustrySP
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterSettingsViewModel

class FilterSettingsFragment : Fragment() {
    private var _binding: FragmentFilterSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentFilter = viewModel.getFilter()
        setupWorkplaceUI(currentFilter.areaCountry, currentFilter.areaCity)
        setupIndustryUI(currentFilter.industrySP)
        setupSalaryAndCheckbox(currentFilter.salary, currentFilter.withSalary)
        setupListeners()
    }

    private fun setupListeners() {
        binding.topBar.setOnClickListener { findNavController().navigate(
            R.id.action_filterSettingsFragment_to_searchFragment
        ) }
        binding.workplaceContainer.setOnClickListener { navigateToPlaceOfWorkFragment() }
        binding.industryContainer.setOnClickListener { navigateToIndustryFragment() }
        binding.resetButton.setOnClickListener { resetButtonClickListener() }
        setupInputSalaryListeners()

        binding.submitButton.setOnClickListener {
            saveFilterAndNavigateToSearch()
        }
    }

    private fun navigateToPlaceOfWorkFragment() {
        findNavController().navigate(R.id.action_filterSettingsFragment_to_filterPlaceOfWorkFragment)
    }

    private fun navigateToIndustryFragment() {
        findNavController().navigate(R.id.action_filterSettingsFragment_to_filterIndustryFragment)
    }

    private fun setupWorkplaceUI(country: Country?, city: City?) {
        updateWorkplaceUI(country, city) { country, city ->
            binding.workplaceBtn.setOnClickListener {
                viewModel.clearFilterField("areaCountry")
                viewModel.clearFilterField("areaCity")
                updateWorkplaceUI(null, null)
                updateSubmitButtonVisibility(true)
            }
        }
    }

    private fun updateWorkplaceUI(country: Country?, city: City?, onClear: ((Country?, City?) -> Unit)? = null) {
        val workplaceText = when {
            country != null && city != null -> "${country.name}, ${city.name}"
            country != null -> country.name
            city != null -> city.name
            else -> null
        }
        with(binding) {
            if (workplaceText != null && isFromInternalScreen()) {
                updateSubmitButtonVisibility(true)
            }
            workplaceValue.text = workplaceText
            workplaceValue.visibility = if (workplaceText != null) View.VISIBLE else View.GONE
            workplaceBtn.setImageResource(
                if (workplaceText != null) R.drawable.ic_close_24px else R.drawable.ic_arrow_forward_24px
            )
            workplacePlaceholder.isVisible = workplaceText == null
            workplaceLabel.isVisible = workplaceText != null
            if (workplaceText == null) {
                workplaceBtn.setOnClickListener { navigateToPlaceOfWorkFragment() }
            } else {
                onClear?.invoke(country, city)
            }
        }
    }

    private fun setupIndustryUI(industrySP: IndustrySP?) {
        updateIndustryUI(industrySP) { industry ->
            binding.industryBtn.setOnClickListener {
                viewModel.clearFilterField("industrySP")
                updateIndustryUI(null)
                updateSubmitButtonVisibility(true)
            }
        }
    }

    private fun updateIndustryUI(industrySP: IndustrySP?, onClear: ((IndustrySP?) -> Unit)? = null) {
        val industryText = industrySP?.name
        with(binding) {
            if (industryText != null && isFromInternalScreen()) {
                updateSubmitButtonVisibility(true)
            }
            industryValue.text = industryText
            industryValue.visibility = if (industryText != null) View.VISIBLE else View.GONE
            industryBtn.setImageResource(
                if (industryText != null) R.drawable.ic_close_24px else R.drawable.ic_arrow_forward_24px
            )
            industryPlaceholder.isVisible = industryText == null
            industryLabel.isVisible = industryText != null
            if (industryText == null) {
                industryBtn.setOnClickListener { navigateToIndustryFragment() }
            } else {
                onClear?.invoke(industrySP)
            }
        }
    }

    private fun setupSalaryAndCheckbox(salary: Int?, withSalary: Boolean?) {
        salary?.let { binding.inputSalary.setText(it.toString()) }
        withSalary?.let { binding.checkBox.isChecked = it }

        binding.inputSalary.doAfterTextChanged { text ->
            if (text?.isNotBlank() == true && text.toString().toInt() != salary) {
                updateSubmitButtonVisibility(true)
            }
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.clearFilterField("withSalary")
            if (isChecked != withSalary) {
                updateSubmitButtonVisibility(true)
            }

        }

        binding.clearSalaryButton.setOnClickListener {
            binding.inputSalary.setText("")
            binding.inputSalary.clearFocus()
            hideKeyboard(binding.root, requireContext())
            updateSubmitButtonVisibility(true)
        }
    }

    private fun hideKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupInputSalaryListeners() {
        var isFocused = false
        binding.inputSalary.doAfterTextChanged { text ->
            if (text != null) {
                binding.clearSalaryButton.isVisible = text.isNotBlank() || isFocused && text.isNotBlank()
            }
        }
        binding.inputSalary.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            isFocused = hasFocus
            binding.hintTitle.isActivated = hasFocus
            binding.clearSalaryButton.isVisible = hasFocus && binding.inputSalary.text?.isNotBlank() == true
        }
    }

    private fun resetButtonClickListener() {
        viewModel.clearFilter()
        updateWorkplaceUI(null, null)
        updateIndustryUI(null)
        binding.inputSalary.text = null
        binding.checkBox.isChecked = false
        updateSubmitButtonVisibility(false)
    }

    private fun isFromInternalScreen(): Boolean {
        val isFromInternalScreen = findNavController().previousBackStackEntry?.destination?.id in setOf(
            R.id.filterPlaceOfWorkFragment,
            R.id.filterIndustryFragment
        )
        return isFromInternalScreen
    }

    private fun updateSubmitButtonVisibility(state: Boolean) {
        binding.submitButton.isVisible = state
    }

    private fun saveFilterAndNavigateToSearch() {
        val salaryText = binding.inputSalary.text

        if (salaryText.toString().toIntOrNull() != null) {
            viewModel.updateFilter(Filter(salary = salaryText.toString().toIntOrNull()))
        } else {
            viewModel.clearFilterField("salary")
        }

        val withSalary = binding.checkBox.isChecked

        if (withSalary) {
            viewModel.updateFilter(Filter(withSalary = true))
        } else {
            viewModel.clearFilterField("withSalary")
        }

        findNavController().navigate(R.id.action_filterSettingsFragment_to_searchFragment)
    }
}
