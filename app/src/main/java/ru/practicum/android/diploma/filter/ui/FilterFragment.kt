package ru.practicum.android.diploma.filter.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.presentation.FilterViewModel

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    val viewModel: FilterViewModel by viewModel()
    private val directionWorkPlace = FilterFragmentDirections.actionFilterFragmentToWorkPlaceFragment()
    private val directionIndustry = FilterFragmentDirections.actionFilterFragmentToIndustryChooserFragment()
    private var colorStateEmpty: ColorStateList? = null
    private var colorStateFilled: ColorStateList? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)

        colorStateEmpty = AppCompatResources.getColorStateList(requireContext(), R.color.salary_field_empty)
        colorStateFilled = AppCompatResources.getColorStateList(requireContext(), R.color.salary_field_filled)

        setListeners()

        viewModel.loadFilterFromPrefs()

        viewModel.state.observe(viewLifecycleOwner) { filter ->
            renderScreen(filter)
        }
    }

    private fun setListeners() {
        setWorkPlaceListeners()
        setIndustryListeners()
        setSalaryListeners()

        with(binding) {
            checkBoxSalary.setOnClickListener {
                viewModel.onlyWithSalaryPressed(checkBoxSalary.isChecked)
            }

            btApply.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    REAPPLY_FILTER,
                    true
                )
                findNavController().navigateUp()
            }

            btClear.setOnClickListener {
                viewModel.clearAll()
            }
        }
    }

    private fun setWorkPlaceListeners() {
        with(binding) {
            edWorkPlace.setOnClickListener {
                findNavController().navigate(directionWorkPlace)
            }

            edWorkPlace.doOnTextChanged { text, _, _, _ ->
                edWorkPlaceLayout.apply {
                    tag = if (text.isNullOrEmpty()) {
                        setEndIconDrawable(R.drawable.ic_arrow_forward)
                        R.drawable.ic_arrow_forward
                    } else {
                        setEndIconDrawable(R.drawable.ic_clear)
                        R.drawable.ic_clear
                    }
                }
            }

            edWorkPlaceLayout.setEndIconOnClickListener {
                if (edWorkPlaceLayout.tag == R.drawable.ic_clear) {
                    viewModel.clearWorkplace()
                } else {
                    findNavController().navigate(directionWorkPlace)
                }
            }

        }
    }

    private fun setIndustryListeners() {
        with(binding) {
            edIndustry.setOnClickListener {
                findNavController().navigate(directionIndustry)
            }

            edIndustry.doOnTextChanged { text, _, _, _ ->
                edIndustryLayout.apply {
                    tag = if (text.isNullOrEmpty()) {
                        setEndIconDrawable(R.drawable.ic_arrow_forward)
                        R.drawable.ic_arrow_forward
                    } else {
                        setEndIconDrawable(R.drawable.ic_clear)
                        R.drawable.ic_clear
                    }
                }
            }

            edIndustryLayout.setEndIconOnClickListener {
                if (edIndustryLayout.tag == R.drawable.ic_clear) {
                    viewModel.clearIndustry()
                } else {
                    findNavController().navigate(directionIndustry)
                }
            }
        }
    }

    private fun setSalaryListeners() {
        var prevText = ""
        with(binding) {
            textInputEditTextSalary.doOnTextChanged { text, _, _, _ ->
                if (text.toString() != prevText) {
                    if (text.isNullOrEmpty()) {
                        textInputLayoutSalary.defaultHintTextColor = colorStateEmpty
                    } else {
                        textInputLayoutSalary.defaultHintTextColor = colorStateFilled
                    }
                    prevText = text.toString()
                    viewModel.updateSalary(prevText)
                }
            }
        }
    }

    private fun renderScreen(filter: FilterParameters) {
        Log.e("renderScreen", filter.toString())
        with(binding) {
            // НАПИСАТЬ КОНВЕРТЕР СТРОКИ
            val country = filter.country?.name
            val area = filter.area?.name
            var place = ""
            if (country != null) {
                place = if (area != null) {
                    "$country, $area"
                } else {
                    "$country"
                }
            }
            // НАПИСАТЬ КОНВЕРТЕР СТРОКИ

            val industry = filter.industry?.name ?: ""
            val salary = filter.salary?.toString() ?: ""
            edWorkPlace.setText(place)
            edIndustry.setText(industry)
            textInputEditTextSalary.setText(salary)
            checkBoxSalary.isChecked = filter.onlyWithSalary
            btApply.isVisible = filter.isNotEmpty
            btClear.isVisible = filter.isNotEmpty
        }
    }

//    private fun addArrowWorkPlace() = with(binding) {
//        AppCompatResources.getColorStateList(requireContext(), R.color.gray)
//            ?.let {
//                edWorkPlaceLayout.setBoxStrokeColorStateList(it)
//                edWorkPlaceLayout.defaultHintTextColor = it
//            }
//        edWorkPlaceLayout.setEndIconDrawable(R.drawable.ic_arrow_forward)
//
//    }

//    private fun addWorkPlace() = with(binding) {
//        if (edWorkPlace.text.isNullOrBlank()) {
//            addArrowWorkPlace()
//        } else {
//            AppCompatResources.getColorStateList(requireContext(), R.color.black_universal)
//                ?.let {
//                    edWorkPlaceLayout.setBoxStrokeColorStateList(it)
//                    edWorkPlaceLayout.defaultHintTextColor = it
//                }
//            edWorkPlaceLayout.apply {
//                setEndIconDrawable(R.drawable.ic_clear)
//                tag = R.drawable.ic_clear
//                btClear.isVisible = true
//                btAdd.isVisible = true
//            }
//        }
//
//    }

//    private fun addArrowIndustry() = with(binding) {
//        AppCompatResources.getColorStateList(requireContext(), R.color.gray)
//            ?.let {
//                edIndustryLayout.setBoxStrokeColorStateList(it)
//                edIndustryLayout.defaultHintTextColor = it
//            }
//        edIndustryLayout.setEndIconDrawable(R.drawable.ic_arrow_forward)
//        edIndustry.setOnClickListener {
//            findNavController().navigate(directionIndustry)
//        }
//        edIndustry.setOnClickListener {
//            findNavController().navigate(directionIndustry)
//        }
//    }
//
//    private fun addIndustry() = with(binding) {
//        if (edIndustry.text.isNullOrBlank()) {
//            addArrowIndustry()
//        } else {
//            AppCompatResources.getColorStateList(requireContext(), R.color.black_universal)
//                ?.let {
//                    edIndustryLayout.setBoxStrokeColorStateList(it)
//                    edIndustryLayout.defaultHintTextColor = it
//                }
//            edIndustryLayout.apply {
//                setEndIconDrawable(R.drawable.ic_clear)
//                tag = R.drawable.ic_clear
//                btClear.isVisible = true
//                btAdd.isVisible = true
//            }
//        }
//        edIndustryLayout.setEndIconOnClickListener {
//            if (edIndustryLayout.tag == R.drawable.ic_clear) {
//                edIndustry.text?.clear()
//                edIndustryLayout.setEndIconDrawable(R.drawable.ic_arrow_forward)
//                AppCompatResources.getColorStateList(requireContext(), R.color.gray)
//                    ?.let {
//                        edIndustryLayout.setBoxStrokeColorStateList(it)
//                        edIndustryLayout.defaultHintTextColor = it
//                    }
//                edIndustry.setOnClickListener {
//                    findNavController().navigate(directionIndustry)
//                }
//                edIndustryLayout.setEndIconOnClickListener {
//                    findNavController().navigate(directionIndustry)
//                }
//            }
//        }
//    }

//    private fun showDefault() = with(binding) {
//        edWorkPlace.text?.clear()
//        edIndustry.text?.clear()
//        textInputEditTextSalary.text?.clear()
//        checkBoxSalary.isChecked = false
//        btClear.isVisible = false
//        btAdd.isVisible = false
//    }

//    private fun listenSalaryEditText() = with(binding) {
//        textInputEditTextSalary.addTextChangedListener {
//            if (!edIndustryLayout.isEmpty()) {
//                btClear.isVisible = true
//                btAdd.isVisible = true
//            }
//        }
//
//        textInputLayoutSalary.setEndIconOnClickListener {
//            textInputEditTextSalary.text?.clear()
//            if (edIndustry.text.isNullOrEmpty() && edWorkPlace.text.isNullOrEmpty()
//                && textInputEditTextSalary.text.isNullOrEmpty()
//            ) {
//                btClear.isVisible = false
//                btAdd.isVisible = false
//            }
//
//        }
//    }

//    private fun checkBoxSalary() = with(binding) {
//        checkBoxSalary.setOnClickListener {
//            if (checkBoxSalary.isChecked) {
//                btClear.isVisible = true
//                btAdd.isVisible = true
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveFilterToPrefs()
    }

    companion object {
        const val REAPPLY_FILTER = "REAPPLY_FILTER"
    }
}