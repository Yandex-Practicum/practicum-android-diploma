package ru.practicum.android.diploma.ui.filter.common.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCommonBinding

class FilterCommonFragment : Fragment() {

    private var _binding: FragmentFilterCommonBinding? = null
    private val binding get() = _binding!!

    private var expectedSalary: Int? = null
    private var withoutSalary: Boolean = false

//    private val viewModel: FilterCommonViewModel by viewModel()

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
    }

    private fun setupListeners() {
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

        binding.salaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            withoutSalary = isChecked
        }

        binding.clearButton.setOnClickListener {
            expectedSalary = null
            binding.salaryEditText.setText(TEXT_EMPTY)
            binding.clearButton.isVisible = false
            hideKeyboard(it)
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

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager? =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val TEXT_EMPTY = ""
    }
}
