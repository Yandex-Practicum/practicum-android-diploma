package ru.practicum.android.diploma.filters.base.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FilterSettingsFragmentBinding
import ru.practicum.android.diploma.filters.base.presentation.FilterSettingsStateScreen
import ru.practicum.android.diploma.filters.base.presentation.FilterSettingsViewModel
import ru.practicum.android.diploma.util.hideKeyboard

class FilterSettingsFragment : Fragment() {
    private var _binding: FilterSettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterSettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkFilterFields()
        observeInit()
        textWatcher()
        onButtonsClicked()
        with(binding) {
            editText.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus()
                    view.hideKeyboard()
                    true
                } else {
                    false
                }
            }

            editText.setOnFocusChangeListener { view, hasFocus ->
                clearButtonVisibility(editText.text)
                if (!hasFocus) view.hideKeyboard()
            }

            salaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setIsShowWithSalary(isChecked)
                viewModel.updateSalaryCheckbox(isChecked)
                editText.clearFocus()
                showApplyAndClearButton()

            }

            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            searchLineCleaner.setOnClickListener {
                clearSalary()
            }

            areaAndIndustryClickListenersInit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.cleanCashArea()
                    findNavController().popBackStack()
                }
            }
        )
    }

    private fun textWatcher() {
        val salaryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // коммент для детекта
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButtonVisibility(p0)
                viewModel.setSalary(p0.toString())
                viewModel.updateSalary(p0.toString())
                showApplyAndClearButton()
            }

            override fun afterTextChanged(editableText: Editable?) {
                // коммент для детекта
            }
        }
        binding.editText.addTextChangedListener(salaryTextWatcher)
    }

    private fun areaAndIndustryClickListenersInit() {
        binding.areaEditText.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterSettingsFragment_to_workingRegionFragment
            )
        }
        binding.areaInputLayout.setEndIconOnClickListener {
            if (binding.areaEditText.text?.isNotBlank() == true) {
                cleanField(binding.areaEditText)
                binding.areaInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                viewModel.cleanCashArea()
                viewModel.clearCurrentArea()
                showApplyAndClearButton()
            } else {
                findNavController().navigate(
                    R.id.action_filterSettingsFragment_to_workingRegionFragment
                )
            }
        }
        binding.industryInputLayout.setEndIconOnClickListener {
            if (binding.industryEditText.text?.isNotBlank() == true) {
                cleanField(binding.industryEditText)
                binding.industryInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                viewModel.clearIndustry()
                viewModel.clearCurrentIndustry()
                showApplyAndClearButton()
            } else {
                findNavController().navigate(
                    R.id.action_filterSettingsFragment_to_industrySelectFragment
                )
            }
        }
        binding.industryEditText.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterSettingsFragment_to_industrySelectFragment
            )
        }
    }

    private fun cleanField(field: TextInputEditText) {
        field.apply {
            setText("")
            isActivated = false
        }

    }

    private fun observeInit() {
        viewModel.getBaseFiltersScreenState.observe(viewLifecycleOwner) { fields ->
            when (fields) {
                is FilterSettingsStateScreen.FilterSettings -> {
                    if (fields.area.isNotBlank()) {
                        binding.areaEditText.apply {
                            isActivated = true
                            setText(fields.area)
                        }
                        binding.areaInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
                    } else {
                        cleanField(binding.areaEditText)
                        binding.areaInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                    }
                    if (fields.industry.isNotBlank()) {
                        binding.industryEditText.apply {
                            setText(fields.industry)
                            isActivated = true
                        }
                        binding.industryInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
                    } else {
                        cleanField(binding.industryEditText)
                        binding.industryInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                    }

                    if (fields.salary.isNotEmpty()) {
                        binding.editText.setText(fields.salary)
                        clearButtonVisibility(fields.salary)
                    }
                    binding.salaryCheckbox.isChecked = fields.showWithSalary
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        val visibility = !s.isNullOrEmpty() && binding.editText.hasFocus()
        binding.searchLineCleaner.isVisible = visibility
    }

    private fun clearSalary() {
        binding.editText.setText(DEF_TEXT)
        binding.editText.requestFocus()

    }

    private fun showApplyAndClearButton() {
        binding.applyButton.isVisible = !viewModel.compareFilters()
        binding.clearFilter.isVisible = viewModel.checkFilter()
    }

    private fun onButtonsClicked() {
        binding.applyButton.setOnClickListener { onApplyButtonClicked() }
        binding.clearFilter.setOnClickListener { onClearFilterClicked() }
    }

    private fun onApplyButtonClicked() {
        viewModel.saveFilters()
        findNavController().popBackStack()
    }

    private fun onClearFilterClicked() {
        with(binding) {
            viewModel.clearFilter()
            viewModel.checkFilterFields()
            editText.setText(DEF_TEXT)
            editText.clearFocus()
            showApplyAndClearButton()

        }
    }

    override fun onResume() {
        super.onResume()
        binding.clearFilter.isVisible = viewModel.checkFilter()
    }

    companion object {
        const val DEF_TEXT = ""
    }
}
