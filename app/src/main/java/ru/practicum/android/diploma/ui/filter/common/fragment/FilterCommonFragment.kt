package ru.practicum.android.diploma.ui.filter.common.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCommonBinding
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.ui.filter.common.viewmodel.FilterCommonViewModel
import ru.practicum.android.diploma.util.FilterNames
import ru.practicum.android.diploma.util.KeyboardUtils

class FilterCommonFragment : Fragment() {

    private var _binding: FragmentFilterCommonBinding? = null
    private val binding get() = _binding!!

    private var expectedSalary: Int? = null
    private var withoutSalary: Boolean = false

    private var selectedIndustry: String? = null // Здесь сохраняем ID отрасли, которое выбрал пользователь

    private val viewModel: FilterCommonViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterCommonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupSalaryField()

        viewModel.acceptButtonLiveData.observe(viewLifecycleOwner) { shouldShowApplyButton ->
            showApplyButton(shouldShowApplyButton)
        }

        viewModel.resetButtonLiveData.observe(viewLifecycleOwner) { shouldShowResetButton ->
            showResetButton(shouldShowResetButton)
        }

        viewModel.filterParamLiveData.observe(viewLifecycleOwner) { filterParametersModel ->
            renderFilters(filterParametersModel)
        }
    }

    private fun setupListeners() {
        setupNavListeners()

        binding.salaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            withoutSalary = isChecked
            viewModel.setIsWithoutSalaryShowed(isChecked)
        }

        binding.clearButton.setOnClickListener {
            expectedSalary = null
            binding.salaryEditText.setText(TEXT_EMPTY)
            binding.clearButton.isVisible = false
            KeyboardUtils.hideKeyboard(requireActivity())
        }

        binding.applyButton.setOnClickListener {
            viewModel.saveFilterSettings()
            findNavController().navigateUp()
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetFilter()
        }

        // Здесь мы слушаем параметры от фрагмента выбора отрасли
        parentFragmentManager.setFragmentResultListener(FilterNames.INDUSTRY_RESULT, viewLifecycleOwner) { _, bundle ->
            selectedIndustry = bundle.getString(FilterNames.INDUSTRY_ID)
            val industryName = bundle.getString(FilterNames.INDUSTRY_NAME)

            viewModel.setIndustryParams(selectedIndustry, industryName) // проверка сохранения отрасли в sp

            binding.industryDefault.text = industryName
        }
    }

    private fun setupNavListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.placeOfWorkDefault.setOnClickListener {
            findNavController().navigate(R.id.action_filterCommonFragment_to_filterCountryRegionFragment)
        }

        binding.placeOfWorkEditedLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filterCommonFragment_to_filterCountryRegionFragment)
        }

        binding.industryDefault.setOnClickListener {
            findNavController().navigate(R.id.action_filterCommonFragment_to_filterIndustryFragment)
        }

        binding.industryEditedLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filterCommonFragment_to_filterIndustryFragment)
        }
    }

    private fun setupSalaryField() {
        binding.salaryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                expectedSalary = binding.salaryEditText.text.toString().takeIf { it.isNotEmpty() }
                    ?.toIntOrNull()

                binding.clearButton.isVisible = clearButtonVisibility(s)
                updateExpectedSalaryTitleTextColor(s)
                viewModel.setExpectedSalaryParam(expectedSalary) // проверка сохранения зп в sp
            }

            override fun afterTextChanged(s: Editable?) {
                // Ничего не делаем
            }
        })
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    private fun updateExpectedSalaryTitleTextColor(s: CharSequence?) {
        if (!s.isNullOrEmpty()) {
            binding.expectedSalaryTitle.setTextColor(getColor(requireContext(), R.color.blue))
        } else {
            binding.expectedSalaryTitle.setTextColor(getColorFromAttr(R.attr.grayToWhite))
        }
    }

    private fun getColorFromAttr(attr: Int): Int {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }

    private fun showApplyButton(isModelsEquals: Boolean) {
        binding.applyButton.isVisible = isModelsEquals
    }

    private fun showResetButton(isModelEmpty: Boolean) {
        binding.resetButton.isVisible = isModelEmpty
    }

    private fun renderFilters(filterParameters: FilterParameters) {
        if (!filterParameters.countryId.isNullOrEmpty()) {
            binding.placeOfWorkDefault.isVisible = false
            binding.placeOfWorkEditedLayout.isVisible = true
            binding.placeOfWorkEditedValue.text = filterParameters.countryName
        }
        if (!filterParameters.regionId.isNullOrEmpty()) {
            binding.placeOfWorkEditedValue.text = filterParameters.regionName
        }
        if (!filterParameters.industryId.isNullOrEmpty()) {
            binding.industryDefault.isVisible = false
            binding.industryEditedLayout.isVisible = true
            binding.industryEditedValue.text = filterParameters.industryName
        }
        val currentSalaryText = binding.salaryEditText.text.toString()
        val newSalaryText = filterParameters.expectedSalary?.toString() ?: TEXT_EMPTY
        if (currentSalaryText != newSalaryText) {
            binding.salaryEditText.setText(newSalaryText)
        }
//        if (filterParameters.expectedSalary != null) {
//            binding.salaryEditText.setText(
//                String.format(Locale.getDefault(),"%d", filterParameters.expectedSalary))
//        }
        binding.salaryCheckbox.isChecked = filterParameters.isWithoutSalaryShowed

    }

    companion object {
        const val TEXT_EMPTY = ""
    }

}
