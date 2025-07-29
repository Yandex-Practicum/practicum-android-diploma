package ru.practicum.android.diploma.search.presenter.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        setupFocusListeners()
        showDarkColorSalary()
        setupSalaryInputClearButton()
    }

    private fun observeViewModel() {
        viewModel.selectedIndustry.onEach { industry ->
            updateIndustryField(industry)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.expectedSalary.onEach { salary ->
            if (binding.editTextId.text.toString() != salary ?: "") {
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
            setFragmentResult(
                "filter_request",
                bundleOf("filters_applied" to false)
            )
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
            setFragmentResult(
                "filter_request",
                bundleOf("filters_applied" to false)
            )
        }

        binding.fieldId.setOnClickListener {
            findNavController().navigate(R.id.action_filtersFragment_to_fieldsFragment)
        }

        binding.industryArrowIcon.setOnClickListener {
            if (viewModel.selectedIndustry.value != null) {
                binding.industryArrowIcon.setImageResource(R.drawable.arrow_right_icon_light)
                binding.filterField.text = getString(R.string.filter_field)
                viewModel.updateIndustry(null)
                viewModel.saveFilters()
            }
        }
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupTextWatchers() {
        binding.editTextId.doAfterTextChanged { text ->
            if (text.toString().isNotBlank()) {
                viewModel.updateSalary(text.toString())
                viewModel.saveFilters()
            } else {
                showGrayColorSalary()
                viewModel.updateSalary(null)
                viewModel.saveFilters()
            }
        }

        binding.noSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateNoSalaryOnly(isChecked)
        }
    }

    private fun updateIndustryField(industry: Industry?) {
        if (industry != null) {
            binding.filterField.text = industry.name
            binding.industryHintId.visibility = View.VISIBLE
            binding.industryArrowIcon.setImageResource(R.drawable.ic_arrow40)
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        } else {
            binding.filterField.text = getString(R.string.filter_field)
            binding.industryHintId.visibility = View.GONE
            binding.industryArrowIcon.setImageResource(R.drawable.arrow_right_icon_light)
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    private fun setupFocusListeners() {
        binding.editTextId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showActiveColorSalary()
                showCrossIc()
            } else {
                showGrayColorSalary()
            }
        }
    }

    private fun showActiveColorSalary() {
        binding.salaryHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun showDarkColorSalary() {
        if (binding.editTextId.text.isNotBlank()) {
            binding.salaryHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun showGrayColorSalary() {
        binding.salaryHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSalaryInputClearButton() {
        binding.editTextId.doAfterTextChanged { text ->
            toggleClearButtonVisibility(text.toString())
            if (text.isNullOrEmpty()) {
                viewModel.updateSalary(null)
                binding.editTextId.clearFocus()
            } else {
                viewModel.updateSalary(text.toString())
            }
            viewModel.saveFilters()
        }

        binding.searchIcon.setOnClickListener {
            binding.editTextId.text?.clear()
            binding.editTextId.clearFocus()
            viewModel.updateSalary(null)
            viewModel.saveFilters()
            binding.salaryHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            hideKeyboard(requireContext(), binding.editTextId)
        }
    }

    private fun toggleClearButtonVisibility(text: String?) {
        binding.searchIcon.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun showCrossIc() {
        if (!binding.editTextId.text.toString().isNullOrEmpty()) {
            binding.searchIcon.visibility = View.VISIBLE
        }
    }
}
