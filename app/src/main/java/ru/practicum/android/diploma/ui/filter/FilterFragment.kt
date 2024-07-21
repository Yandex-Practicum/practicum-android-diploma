package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.util.debounce

class FilterFragment : Fragment() {

    private var textWatcher: TextWatcher? = null
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.workPlaceTv.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industryTv.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }

        val (emptyHintColor, blackHintColor, blueHintColor) = hintColorStates()

        val (debounceBlackColor, debounceEmptyColor, debounceBlueColor) = hintDebouncers(
            blackHintColor,
            emptyHintColor,
            blueHintColor
        )

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.salaryTv.requestFocus()
                if (binding.salaryTv.hasFocus() && s?.isNotEmpty() == true) {
                    binding.salaryFrame.defaultHintTextColor = blackHintColor
                    binding.salaryFrame.setEndIconDrawable(R.drawable.close_icon)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.salaryTv.hasFocus() && s?.isNotEmpty() == true) {
                    binding.salaryFrame.defaultHintTextColor = blueHintColor
                    binding.salaryFrame.setEndIconDrawable(R.drawable.close_icon)
                    debounceBlackColor(s.toString())
                } else if (s?.isEmpty() == true) {
                    binding.salaryFrame.defaultHintTextColor = emptyHintColor
                    binding.salaryFrame.endIconDrawable = null
                    debounceEmptyColor(s.toString())
                }
            }
        }

        binding.salaryTv.addTextChangedListener(textWatcher!!)
        binding.salaryFrame.setEndIconOnClickListener {
            binding.salaryTv.setText(getString(R.string.empty_string))
            binding.salaryFrame.defaultHintTextColor = emptyHintColor
            debounceEmptyColor("")
            it.hideKeyboard()
        }
        binding.resetButton.setOnClickListener {
            binding.salaryTv.text = null
            binding.salaryFrame.endIconDrawable = null
            viewModel.clearFilter()
            binding.salaryFrame.defaultHintTextColor = emptyHintColor
            debounceEmptyColor("")
        }

        binding.salaryIsRequiredCV.setOnClickListener {
            if (checkSalaryRequired()) {
                viewModel.setSalaryIsRequired(true)
                viewModel.showSaveButton()
            } else {
                viewModel.setSalaryIsRequired(false)
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveFilter(
                binding.workPlaceTv.text.toString(),
                binding.industryTv.text.toString(),
                binding.salaryTv.text.toString(),
                checkSalaryRequired()
            )
            findNavController().popBackStack()
        }
    }

    private fun checkSalaryRequired(): Boolean {
        return binding.salaryIsRequiredCV.isChecked
    }

    private fun render(state: FilterState) {
        when (state) {
            FilterState.Default -> defaultScreen()
            is FilterState.Filtered -> filterScreen(state.area, state.industry, state.salary, state.isSalaryRequired)
            FilterState.readyToSave -> showSaveButton()
        }
    }

    private fun showSaveButton() {
        binding.resetButton.isVisible = true
        binding.saveButton.isVisible = true
    }

    private fun filterScreen(area: String, industry: String, salary: String, salaryRequired: Boolean) {
        binding.workPlaceTv.setText(area)
        binding.industryTv.setText(industry)
        binding.salaryTv.setText(salary)
        binding.salaryIsRequiredCV.isChecked = salaryRequired
        fillWorkPlace()
        fillIndustry()
        binding.saveButton.isVisible = true
        binding.resetButton.isVisible = true
    }

    private fun fillWorkPlace() {
        if (binding.workPlaceTv.text.toString().isNotEmpty()) {
            binding.workPlace.setEndIconDrawable(R.drawable.close_icon)
            binding.workPlace.defaultHintTextColor = hintColorStates().second
            binding.workPlace.setEndIconOnClickListener {
                viewModel.clearWorkplace()
                binding.workPlaceTv.setText(getString(R.string.empty_string))
                binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
                binding.workPlace.defaultHintTextColor = hintColorStates().first
                binding.workPlace.setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
                }
            }
        } else {
            binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
            binding.workPlace.defaultHintTextColor = hintColorStates().first
            binding.workPlace.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
            }
        }
    }

    private fun fillIndustry() {
        if (binding.industryTv.text.toString().isNotEmpty()) {
            binding.industry.setEndIconDrawable(R.drawable.close_icon)
            binding.industry.defaultHintTextColor = hintColorStates().second
            binding.industry.setEndIconOnClickListener {
                viewModel.clearIndustry()
                binding.industryTv.setText(getString(R.string.empty_string))
                binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
                binding.industry.defaultHintTextColor = hintColorStates().first
                binding.industry.setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
                }
            }
        } else {
            binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
            binding.industry.defaultHintTextColor = hintColorStates().first
            binding.industry.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
            }
        }
    }

    private fun defaultScreen() {
        binding.resetButton.isVisible = false
        binding.saveButton.isVisible = false
        binding.workPlaceTv.setText(getString(R.string.empty_string))
        binding.industryTv.setText(getString(R.string.empty_string))
        binding.salaryTv.setText(getString(R.string.empty_string))
        binding.salaryIsRequiredCV.isChecked = false
        binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
        binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
    }

    private fun hintDebouncers(
        blackHintColor: ColorStateList,
        emptyHintColor: ColorStateList,
        blueHintColor: ColorStateList
    ): Triple<(String?) -> Unit, (String?) -> Unit, (String?) -> Unit> {
        val debounceBlackColor = debounceHint(blackHintColor)
        val debounceEmptyColor = debounceHint(emptyHintColor)
        val debounceBlueColor = debounceHint(blueHintColor)
        return Triple(debounceBlackColor, debounceEmptyColor, debounceBlueColor)
    }

    private fun debounceHint(emptyHintColor: ColorStateList) = debounce<String?>(
        SALARY_DEBOUNCE_DELAY,
        viewLifecycleOwner.lifecycleScope,
        true
    ) { s ->
        binding.salaryFrame.defaultHintTextColor = emptyHintColor

    }

    private fun hintColorStates(): Triple<ColorStateList, ColorStateList, ColorStateList> {
        val emptyHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color))
        )

        val blackHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_after))
        )

        val blueHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_blue))
        )
        return Triple(emptyHintColor, blackHintColor, blueHintColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        const val SALARY_DEBOUNCE_DELAY = 300L
    }

    override fun onResume() {
        super.onResume()
        binding.salaryFrame.defaultHintTextColor = hintColorStates().first
    }
}

