package ru.practicum.android.diploma.filter.filter.presentation.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.presentation.ui.uimanager.ColorManager
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

internal class FilterFragment : Fragment() {

    private val viewModel: FilterViewModel by viewModel()

    private val colorManager: ColorManager by lazy { ColorManager(requireContext()) }
    private val colorsEditTextFilterEmpty by lazy { colorManager.getColorsForEditText(true) }
    private val colorsEditTextFilterNoEmpty by lazy { colorManager.getColorsForEditText(false) }
    private val statesEditTextFilter by lazy { colorManager.getStatesEditTextFilter() }

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        viewModel.getBufferDataFromSpAndCompareFilterSettings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filterOptionsBufferLiveData.observe(viewLifecycleOwner) { filter ->
            render(filter)
        }

        viewModel.newSettingsFilterLiveData.observe(viewLifecycleOwner) { newSettingsFilter ->
            binding.buttonApply.visibility = if (newSettingsFilter) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        setupClickListeners()

        binding.editTextFilter.setOnEditorActionListener(editorActionListener)
        binding.editTextFilter.addTextChangedListener(inputSearchWatcher)

        binding.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            viewModel.setDoNotShowWithoutSalaryInDataFilterBuffer(isChecked)
        }
    }
    @Suppress("LongMethod")
    private fun setupClickListeners() {
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.workPlace, R.id.inputWorkPlaceLayout, R.id.inputWorkPlace, R.id.clickWorkPlace -> {
                    findNavController().navigate(R.id.action_filterFragment_to_placeFragment)
                }
                R.id.workIndustry, R.id.inputWorkIndustryLayout, R.id.inputWorkIndustry, R.id.clickWorkIndustry -> {
                    findNavController().navigate(R.id.action_filterFragment_to_industryFragment)
                }
                R.id.clickWorkPlaceClear -> {
                    viewModel.clearPlaceInDataFilterBuffer()
                    renderPlaceFilterClear()
                }
                R.id.clickWorkIndustryClear -> {
                    viewModel.clearProfessionInDataFilterBuffer()
                    renderProfessionFilterClear()
                }
                R.id.buttonApply -> {
                    viewModel.copyDataFilterBufferInDataFilter()
                    findNavController().navigateUp()
                }
                R.id.buttonBack -> {
                    viewModel.copyDataFilterInDataFilterBuffer()
                    findNavController().navigateUp()
                }
                R.id.buttonCancel -> {
                    viewModel.clearDataFilterAll()
                    findNavController().navigateUp()
                }
            }
        }

        listOf(
            binding.workPlace,
            binding.inputWorkPlaceLayout,
            binding.inputWorkPlace,
            binding.clickWorkPlace,
            binding.workIndustry,
            binding.inputWorkIndustryLayout,
            binding.inputWorkIndustry,
            binding.clickWorkIndustry,
            binding.clickWorkPlaceClear,
            binding.clickWorkIndustryClear,
            binding.buttonBack,
            binding.buttonApply,
            binding.buttonCancel
        ).forEach { it.setOnClickListener(clickListener) }
    }

    private fun visibleClearFilter(filter: FilterSettings?) {
        binding.buttonCancel.visibility = if (filter != null && !filter.equals(emptyFilterSetting())) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val inputSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onTextChanged(inputText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val colors = if (inputText.isNullOrEmpty()) colorsEditTextFilterEmpty else colorsEditTextFilterNoEmpty
            binding.textViewSalary.hintTextColor = ColorStateList(statesEditTextFilter, colors)
            binding.textViewSalary.setDefaultHintTextColor(ColorStateList(statesEditTextFilter, colors))
        }

        override fun afterTextChanged(resultText: Editable?) {
            //
        }
    }

    @SuppressLint("ResourceAsColor")
    private val editorActionListener: OnEditorActionListener = OnEditorActionListener { v, actionId, event ->
        val isDoneAction = actionId == EditorInfo.IME_ACTION_DONE
        val isEnterKeyPressed = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
        if (isDoneAction || isEnterKeyPressed) {
            v.clearFocus()
            requireContext().closeKeyBoard(v)
            val inputSalary = binding.editTextFilter.text
            val textSalary = if (inputSalary.isNullOrEmpty()) "" else inputSalary.toString()
            viewModel.setSalaryInDataFilterBuffer(textSalary)
            true
        } else {
            false
        }
    }

    private fun render(filter: FilterSettings) {
        visibleClearFilter(filter)
        renderPlaceFilter(filter)
        renderProfessionFilter(filter)
        renderExpectedSalaryFilter(filter)
        renderDoNotShowWithoutSalaryFilter(filter)
    }

    private fun renderDoNotShowWithoutSalaryFilter(filter: FilterSettings) {
        binding.checkBox.isChecked = filter.doNotShowWithoutSalary
    }

    private fun renderExpectedSalaryFilter(filter: FilterSettings) {
        val salary = filter.expectedSalary
        if (salary.isNullOrEmpty()) {
            binding.editTextFilter.text?.clear()
        } else {
            binding.editTextFilter.setText(salary)
        }
    }

    private fun renderProfessionFilter(filter: FilterSettings) {
        val profession = filter.branchOfProfession
        if (profession != null && !profession.name.isNullOrEmpty()) {
            binding.inputWorkIndustry.setText(profession.name)
            binding.clickWorkIndustry.isGone = true
            binding.clickWorkIndustryClear.isVisible = true
        } else {
            renderProfessionFilterClear()
        }
    }

    private fun renderProfessionFilterClear() {
        binding.inputWorkIndustry.text?.clear()
        binding.clickWorkIndustry.isVisible = true
        binding.clickWorkIndustryClear.isGone = true
    }

    private fun renderPlaceFilter(filter: FilterSettings) {
        val place = filter.placeSettings
        if (place != null) {
            when {
                place.nameCountry != null && place.nameRegion != null -> {
                    binding.inputWorkPlace.setText(
                        getString(
                            ru.practicum.android.diploma.ui.R.string.filter_place_form,
                            place.nameCountry,
                            place.nameRegion
                        )
                    )
                    binding.clickWorkPlace.isGone = true
                    binding.clickWorkPlaceClear.isVisible = true
                }

                place.nameCountry != null && place.nameRegion == null -> {
                    binding.inputWorkPlace.setText(place.nameCountry)
                    binding.clickWorkPlace.isGone = true
                    binding.clickWorkPlaceClear.isVisible = true
                }

                else -> {
                    renderPlaceFilterClear()
                }
            }
        } else {
            renderPlaceFilterClear()
        }
    }

    private fun renderPlaceFilterClear() {
        binding.inputWorkPlace.text?.clear()
        binding.clickWorkPlace.isVisible = true
        binding.clickWorkPlaceClear.isGone = true
    }

    private fun emptyFilterSetting(): FilterSettings {
        return FilterSettings.emptyFilterSettings()
    }

    override fun onDestroy() {
        super.onDestroy()
        inputSearchWatcher.let { binding.editTextFilter.removeTextChangedListener(it) }
        _binding = null
    }
}
