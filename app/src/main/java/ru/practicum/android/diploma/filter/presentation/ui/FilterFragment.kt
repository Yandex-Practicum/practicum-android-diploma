package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.search.presentation.ui.SearchFragmentDirections

class FilterFragment: Fragment() {
    private lateinit var binding: FragmentFilterBinding

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miFilterLocation.editText?.setText("Россия, Москва")
        setMenuEditTextStyle(binding.miFilterLocation, true)
        setMenuEditTextStyle(binding.miFilterIndustry, false)
        setSalaryEditTextStyle(binding.miFilterSalary, false)

        binding.miFilterLocation.editText?.setOnClickListener {
            showLocation()
        }

        binding.miFilterIndustry.editText?.setOnClickListener {
            showIndustry()
        }

        binding.miFilterLocation.setEndIconOnClickListener {
            if (binding.miFilterLocation.editText?.text.isNullOrEmpty())
                showLocation()
            else {
                binding.miFilterLocation.editText?.text = null
                setMenuEditTextStyle(binding.miFilterLocation, false)
            }
        }

        binding.miFilterIndustry.setEndIconOnClickListener {
            if (binding.miFilterIndustry.editText?.text.isNullOrEmpty())
                showIndustry()
            else {
                binding.miFilterIndustry.editText?.text = null
                setMenuEditTextStyle(binding.miFilterIndustry, false)
            }

        }

        binding.miFilterSalary.setEndIconOnClickListener {
            binding.miFilterSalary.editText?.text = null
            it.visibility = View.GONE
        }

        binding.miFilterSalary.editText?.doOnTextChanged { text, _, _, _ ->
            setSalaryEditTextStyle(binding.miFilterSalary, !text.isNullOrEmpty())
        }
    }

    private fun setMenuEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_menu_label_selector
        } else {
            R.color.filter_menu_hint_selector
        }

        val colorStateList = ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        if (filled) {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_clear)
        } else {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_arrow_forward)
        }
    }

    private fun setSalaryEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_salary_label_selector
        } else {
            R.color.filter_salary_hint_selector
        }

        val colorStateList = ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        textInputLayout.isEndIconVisible = filled
    }

    private fun showLocation() {
        Log.e("filter", findNavController().currentDestination.toString())
        val action = FilterFragmentDirections.actionFilterFragmentToFilterLocationFragment2(
            // location
        )
       findNavController().navigate(action)
    }

    private fun showIndustry() {
        //
    }
}