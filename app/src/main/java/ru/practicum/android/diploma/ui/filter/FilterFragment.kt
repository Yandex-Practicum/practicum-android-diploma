package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.ui.filter.model.FilterScreenState
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters
import ru.practicum.android.diploma.ui.filter.place.PlaceFilterFragment
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.COUNTRY_KEY
import ru.practicum.android.diploma.util.REGION_KEY
import ru.practicum.android.diploma.util.formatPlace

class FilterFragment : BindingFragment<FragmentFilterBinding>() {

    private val viewModel: FilterViewModel by viewModel()
    private val args by navArgs<FilterFragmentArgs>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiToolbar()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(true)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Country?>(COUNTRY_KEY)
            ?.observe(viewLifecycleOwner) { country ->
                viewModel.setCountry(country)
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Region?>(REGION_KEY)
            ?.observe(viewLifecycleOwner) { region ->
                viewModel.setRegion(region)
            }
        initScreen()
        viewModel.screenInit(binding, requireContext())
        viewModel.getFilters()
        args.selectedIndustryId?.let { id ->
            val name = args.selectedIndustryName
            if (name != null) {
                viewModel.setIndustry(id, name)
            }
        }
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is FilterScreenState.CONTENT -> showContent(state.value)
            }
        }
    }

    private fun initUiToolbar() {
        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(true)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()
    }

    private fun initScreen() {
        initListeners()
        initListenersSalaryAndBtns()
    }

    private fun initListeners() {
        binding.includedPlace.root.setOnClickListener {
            val state = viewModel.getState().value
            viewModel.saveFilters()
            if (state is FilterScreenState.CONTENT) {
                findNavController().navigate(
                    R.id.action_filterFragment_to_placeFilterFragment,
                    PlaceFilterFragment.createArgs(state.value.country, state.value.region)
                )
            } else {
                findNavController().navigate(
                    R.id.action_filterFragment_to_placeFilterFragment,
                    PlaceFilterFragment.createArgs(null, null)
                )
            }
        }
        binding.includedPlace.itemIcon.setOnClickListener {
            if (binding.includedPlace.itemText.text.isNotEmpty()) {
                viewModel.clearPlace()
            }
        }
        binding.includedIndustry.root.setOnClickListener {
            viewModel.saveFilters()
            findNavController().navigate(R.id.action_filterFragment_to_industryFilterFragment)
        }
        binding.includedIndustry.itemIcon.setOnClickListener {
            if (binding.includedIndustry.itemText.text.isNotEmpty()) {
                viewModel.clearIndustry()
            }
        }
        binding.includedSalary.textFieldClear.setOnClickListener {
            viewModel.clearSalary()
        }
        binding.includedShowNoSalary.checkbox.setOnClickListener {
            viewModel.setShowNoSalary()
        }
    }

    private fun initListenersSalaryAndBtns() {
        binding.includedSalary.textFieldEdit.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                viewModel.setSalary(binding.includedSalary.textFieldEdit.text.toString().toIntOrNull())
                true
            } else {
                false
            }
        }
        binding.includedSalary.textFieldEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.includedSalary.textFieldHeader.text = requireContext().getString(R.string.expected_salary)
                binding.includedSalary.textFieldHeader.setTextColor(requireContext().getColor(R.color.blue))
            } else {
                if (binding.includedSalary.textFieldEdit.text.isEmpty()) {
                    binding.includedSalary.textFieldHeader.text = ""
                }
                binding.includedSalary.textFieldHeader.setTextColor(requireContext().getColor(R.color.black))
            }
        }
        binding.includedBtnSet.root.setOnClickListener {
            viewModel.setSalary(binding.includedSalary.textFieldEdit.text.toString().toIntOrNull())
            viewModel.saveFilters()
            findNavController().popBackStack()
        }
        binding.includedBtnCancel.root.setOnClickListener {
            viewModel.clearFilters()
            viewModel.saveFilters()
            findNavController().popBackStack()
        }
    }

    private fun showContent(filters: SelectedFilters) {
        val place = formatPlace(filters)
        fillPlace(place)
        fillIndustry(filters.industry)
        fillSalary(filters.salary)
        setShowNoSalary(filters.onlyWithSalary)
        setButtonsVisibility(
            !place.isNullOrEmpty()
                || !filters.industry.isNullOrEmpty() || filters.salary != null || filters.onlyWithSalary
        )
    }

    private fun fillPlace(place: String?) {
        val hasValue = !place.isNullOrEmpty()
        binding.includedPlace.apply {
            itemTextTop.isVisible = hasValue
            itemIcon.setImageResource(
                if (hasValue) {
                    R.drawable.close_24px
                } else {
                    R.drawable.arrow_forward_24px
                }
            )
            itemText.text = if (hasValue) {
                place
            } else {
                ""
            }
        }
    }

    private fun fillIndustry(industry: String?) {
        val hasValue = !industry.isNullOrEmpty()
        binding.includedIndustry.apply {
            itemTextTop.isVisible = hasValue
            itemIcon.setImageResource(
                if (hasValue) {
                    R.drawable.close_24px
                } else {
                    R.drawable.arrow_forward_24px
                }
            )
            itemText.text = if (hasValue) {
                industry
            } else {
                ""
            }
        }
    }

    private fun fillSalary(salary: Int?) {
        binding.includedSalary.apply {
            if (salary != null) {
                textFieldHeader.text = requireContext().getString(R.string.expected_salary)
                textFieldEdit.setText(salary.toString())
                textFieldClear.isVisible = true
            } else {
                textFieldHeader.text = ""
                textFieldEdit.setText("")
                textFieldClear.isVisible = false
            }
        }
    }

    private fun setShowNoSalary(showNoSalary: Boolean) {
        binding.includedShowNoSalary.checkbox.setImageResource(
            if (showNoSalary) {
                R.drawable.check_box_on__24px
            } else {
                R.drawable.check_box_off__24px
            }
        )
    }

    private fun setButtonsVisibility(visibility: Boolean) {
        binding.includedBtnSet.root.isVisible = visibility
        binding.includedBtnCancel.root.isVisible = visibility
    }
}
