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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.model.IndustrySetting
import ru.practicum.android.diploma.filter.filter.domain.model.PlaceSettings
import ru.practicum.android.diploma.filter.filter.presentation.ui.uimanager.ColorManager
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

internal class FilterFragment : Fragment() {

    private val viewModel: FilterViewModel by viewModel()
    private var filterSettings: FilterSettings = emptyFilterSetting()

    private val colorManager: ColorManager by lazy { ColorManager(requireContext()) }
    private val colorsEditTextFilterEmpty by lazy { colorManager.getColorsForEditText(true) }
    private val colorsEditTextFilterNoEmpty by lazy { colorManager.getColorsForEditText(false) }
    private val statesEditTextFilter by lazy { colorManager.getStatesEditTextFilter() }

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        viewModel.getDataFromSp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filterOptionsListLiveData.observe(viewLifecycleOwner) { filter ->
            filterSettings = filter
            render(filter)
            visibleClearFilter(filter)
        }

        setupClickListeners()

        binding.editTextFilter.setOnEditorActionListener(editorActionListener)
        binding.editTextFilter.addTextChangedListener(inputSearchWatcher)

        binding.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            filterSettings = filterSettings.copy(doNotShowWithoutSalary = isChecked)
            checkingFilterChanges()
        }
    }

    private fun setupClickListeners() {
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.workPlace, R.id.inputWorkPlaceLayout, R.id.inputWorkPlace, R.id.clickWorkPlace -> {
                    findNavController().navigate(R.id.action_filterFragment_to_placeFragment)
                }
                R.id.workIndustry, R.id.inputWorkIndustryLayout, R.id.inputWorkIndustry, R.id.clickWorkIndustry -> {
                    findNavController().navigate(R.id.action_filterFragment_to_industryFragment)
                }
                R.id.clickWorkPlaceClear -> renderPlaceFilterClear()
                R.id.clickWorkIndustryClear -> renderProfessionFilterClear()
                R.id.buttonApply -> {
                    viewModel.setSalaryInDataFilter(binding.editTextFilter.text.toString())
                    viewModel.setDoNotShowWithoutSalaryInDataFilter(binding.checkBox.isChecked)
                    clearParameters(filterSettings)
                    findNavController().navigateUp()
                }
                R.id.buttonBack -> findNavController().navigateUp()
                R.id.buttonCancel -> {
                    viewModel.clearDataFilter()
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

    private fun checkingFilterChanges() {
        visibleClearFilter(filterSettings)
        binding.buttonApply.visibility = if (!filterSettings.equals(viewModel.filterSettingsUI)) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val inputSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val text = oldText.toString()
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onTextChanged(inputText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val colors = if (inputText.isNullOrEmpty()) colorsEditTextFilterEmpty else colorsEditTextFilterNoEmpty
            binding.textViewSalary.hintTextColor = ColorStateList(statesEditTextFilter, colors)
            binding.textViewSalary.setDefaultHintTextColor(ColorStateList(statesEditTextFilter, colors))
            val textSalary = if (inputText.isNullOrEmpty()) null else inputText.toString()
            filterSettings = filterSettings.copy(expectedSalary = textSalary)
            checkingFilterChanges()
        }

        override fun afterTextChanged(resultText: Editable?) {
            val text = resultText.toString()
        }
    }

    @SuppressLint("ResourceAsColor")
    private val editorActionListener: OnEditorActionListener = OnEditorActionListener { v, actionId, event ->
        val isDoneAction = actionId == EditorInfo.IME_ACTION_DONE
        val isEnterKeyPressed = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
        if (isDoneAction || isEnterKeyPressed) {
            v.clearFocus()
            requireContext().closeKeyBoard(v)
            true
        } else {
            false
        }
    }

    private fun render(filter: FilterSettings) {
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
        if (!salary.isNullOrEmpty()) {
            binding.editTextFilter.setText(salary)
        } else {
            binding.editTextFilter.text?.clear()
        }
    }

    private fun renderProfessionFilter(filter: FilterSettings) {
        val profession = filter.branchOfProfession
        if (profession != null && !profession.name.isNullOrEmpty()) {
            binding.inputWorkIndustry.setText(profession.name)
            binding.clickWorkIndustry.visibility = View.GONE
            binding.clickWorkIndustryClear.visibility = View.VISIBLE
        } else {
            renderProfessionFilterClear()
        }
    }

    private fun renderProfessionFilterClear() {
        filterSettings = filterSettings.copy(
            branchOfProfession = IndustrySetting(
                null,
                null
            )
        )
        checkingFilterChanges()
        binding.inputWorkIndustry.text?.clear()
        binding.clickWorkIndustry.visibility = View.VISIBLE
        binding.clickWorkIndustryClear.visibility = View.GONE
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
                    binding.clickWorkPlace.visibility = View.GONE
                    binding.clickWorkPlaceClear.visibility = View.VISIBLE
                }

                place.nameCountry != null && place.nameRegion == null -> {
                    binding.inputWorkPlace.setText(place.nameCountry)
                    binding.clickWorkPlace.visibility = View.GONE
                    binding.clickWorkPlaceClear.visibility = View.VISIBLE
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
        filterSettings = filterSettings.copy(
            placeSettings = PlaceSettings(
                null,
                null,
                null,
                null
            )
        )
        checkingFilterChanges()
        binding.inputWorkPlace.text?.clear()
        binding.clickWorkPlace.visibility = View.VISIBLE
        binding.clickWorkPlaceClear.visibility = View.GONE
    }

    private fun emptyFilterSetting(): FilterSettings {
        return FilterSettings(
            placeSettings = PlaceSettings(null, null, null, null),
            branchOfProfession = IndustrySetting(null, null),
            expectedSalary = null,
            doNotShowWithoutSalary = false
        )
    }

    private fun clearParameters(filter: FilterSettings) {
        if (filter.placeSettings?.idCountry == null) {
            viewModel.clearPlaceInDataFilter()
        }
        if (filter.branchOfProfession?.id == null) {
            viewModel.clearProfessionInDataFilter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        inputSearchWatcher.let { binding.editTextFilter.removeTextChangedListener(it) }
        _binding = null
    }
}
