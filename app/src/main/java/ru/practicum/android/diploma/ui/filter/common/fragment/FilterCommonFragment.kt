package ru.practicum.android.diploma.ui.filter.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCommonBinding

class FilterCommonFragment : Fragment() {

    private var _binding: FragmentFilterCommonBinding? = null
    private val binding get() = _binding!!

//    private val salaryTextWatcher: TextWatcher by lazy {
//        createSalaryTextWatcher()
//    }

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
//        setupSalaryField()
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

//        binding.clearButton.setOnClickListener {
// //            binding.salaryEditText.removeTextChangedListener(salaryTextWatcher)
//            binding.salaryEditText.setText("")
//            // текст меняется на пустой и перезаписывается
//            expectedSalary = null
// //            binding.salaryEditText.addTextChangedListener(salaryTextWatcher)
//            hideKeyboard(it)
//        }
    }

//    private fun setupSalaryField() {
//        binding.salaryEditText.addTextChangedListener(salaryTextWatcher)
//
//        binding.salaryEditText.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                // текст поменялся
//                expectedSalary = null
//            }
//            false
//        }
//
//        binding.salaryEditText.setOnFocusChangeListener { _, hasFocus ->
//
//            if (hasFocus && binding.salaryEditText.text.isEmpty()) {
//                // Текст поменялся на пустой
//                expectedSalary = null
//            }
//            // текст НЕ ПУСТОЙ
//        }
//    }
//
//    private fun createSalaryTextWatcher(): TextWatcher {
//        return object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
// //                if (!charSequence.isNullOrEmpty()) {
// //                    // ТЕКСТ ПОМЕНЯЛСЯ И НЕ ПУСТОЙ
// //                    expectedSalary = charSequence.toString().toInt()
// //                }
// //                expectedSalary = charSequence?.toString()?.toIntOrNull()
// //                binding.clearButton.isVisible = clearButtonVisibility(charSequence)
//
//                if (binding.salaryEditText.hasFocus() && charSequence?.isEmpty() == true) {
//                    // ТЕКСТ ПОМЕНЯЛСЯ НА ПУСТОЙ
//                    expectedSalary = null
//                }
//
//                // ТЕКСТ ПОМЕНЯЛСЯ
//                expectedSalary = charSequence?.toString()?.toInt()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//        }
//    }
//
// //    private fun clearButtonVisibility(s: CharSequence?): Boolean {
// //        return !s.isNullOrEmpty()
// //    }
//
//    private fun hideKeyboard(view: View) {
//        val inputMethodManager: InputMethodManager? =
//            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
//        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
// //    companion object {
// //        const val TEXT_EMPTY = ""
// //    }
}
