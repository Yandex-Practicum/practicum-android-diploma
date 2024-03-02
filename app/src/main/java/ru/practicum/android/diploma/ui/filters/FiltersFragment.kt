package ru.practicum.android.diploma.ui.filters

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings
import ru.practicum.android.diploma.presentation.filters.FiltersViewModel
import ru.practicum.android.diploma.ui.search.gone
import ru.practicum.android.diploma.ui.search.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.ui.filter.ChooseIndustryFragment

class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltersViewModel by viewModel()
    private lateinit var filterSettings: FiltersSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtonListeners()
        initTextListeners()
        initFilterSettings()

        binding.industryLayout.setOnClickListener {
            findNavController().navigate(R.id.chooseIndustryFragment)
        }
        parentFragmentManager.setFragmentResultListener(ChooseIndustryFragment.REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            val industry = bundle.getString(ChooseIndustryFragment.INDUSTRY_KEY)
            binding.industry.setText(industry)
        }
    }

    private fun initButtonListeners() {
        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.placeOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterPlaceOfWorkFragment)
        }

        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterIndustryFragment)
        }

        binding.applyButton.setOnClickListener {
            savePrefs()
            initFilterSettings()
            binding.applyButton.gone()
        }

        binding.resetButton.setOnClickListener {
            resetFilters()
        }

        binding.clearButton.setOnClickListener {
            binding.expectedSalary.setText("")
            hideKeyboard()
        }

        binding.salaryOnlyCheckbox.setOnClickListener {
            buttonsVisibility()
        }
    }

    private fun initTextListeners() {
        binding.expectedSalary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearTextButtonVisibility()
                buttonsVisibility()
            }

            override fun afterTextChanged(p0: Editable?) { }
        }
        )

        binding.placeOfWork.doOnTextChanged { text, start, before, count ->
            buttonsVisibility()
        }

        binding.industry.doOnTextChanged { text, start, before, count ->
            buttonsVisibility()
        }
    }

    private fun initFilterSettings() {
        filterSettings = viewModel.getPrefs()
        binding.placeOfWork.setText(filterSettings.placeOfWork)
        binding.industry.setText(filterSettings.industry)
        binding.expectedSalary.setText(filterSettings.expectedSalary)
        binding.salaryOnlyCheckbox.isChecked = filterSettings.salaryOnlyCheckbox
        buttonsVisibility()
    }

    private fun savePrefs() {
        viewModel.savePrefs(
            FiltersSettings(
                binding.placeOfWork.text.toString(),
                binding.industry.text.toString(),
                binding.expectedSalary.text.toString(),
                binding.salaryOnlyCheckbox.isChecked
            )
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.expectedSalary.windowToken, 0)
    }

    private fun clearTextButtonVisibility() = if (binding.expectedSalary.text.isNullOrEmpty()) {
        binding.clearButton.gone()
    } else {
        binding.clearButton.visible()
    }

    private fun buttonsVisibility() {
        if (binding.placeOfWork.text!!.isNotEmpty() || binding.industry.text!!.isNotEmpty() ||
             binding.expectedSalary.text!!.isNotEmpty() || binding.salaryOnlyCheckbox.isChecked
            ) {
            binding.resetButton.visible()
        } else {
            binding.resetButton.gone()
        }

        if (binding.placeOfWork.text.toString().equals(filterSettings.placeOfWork) &&
            binding.industry.text.toString().equals(filterSettings.industry) &&
            binding.expectedSalary.text.toString().equals(filterSettings.expectedSalary) &&
            binding.salaryOnlyCheckbox.isChecked == filterSettings.salaryOnlyCheckbox
        ) {
            binding.applyButton.gone()
        } else {
            binding.applyButton.visible()
            Log.d("filters_salaryOnlyCheckbox", binding.salaryOnlyCheckbox.isChecked.toString())
            Log.d("prefs_salaryOnlyCheckbox", filterSettings.salaryOnlyCheckbox.toString())
        }
    }

    private fun resetFilters() {
        binding.placeOfWork.setText("")
        binding.industry.setText("")
        binding.expectedSalary.setText("")
        binding.salaryOnlyCheckbox.isChecked = false
        binding.resetButton.gone()
        viewModel.clearPrefs()
        initFilterSettings()
    }
}
