package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.view_models.DepartmentViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName

class DepartmentFragment : ChooseFragment() {
    
    override val fragment = DEPARTMENT
    private val args by navArgs<CountryFragmentArgs>()
    override val viewModel: DepartmentViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedFilter = args.selectedFilter
        super.onViewCreated(view, savedInstanceState)
    }
    @Suppress("UNCHECKED_CAST")
    override fun renderContent(list: List<Any?>) {
        super.renderContent(list)
        binding.toolbar.title = requireActivity().getString(R.string.choose_department)
        filterAdapter.industryList = list as List<Industry>
        binding.inputLayout.visibility = View.VISIBLE
        filterAdapter.notifyDataSetChanged()
        viewModel.log(thisName, "renderContent: list.size = ${list.size})")
    }
    
    override fun initListeners() {
        super.initListeners()
        filterAdapter.onClickIndustry = { industry ->
            super.hideKeyboard()
            viewModel.saveIndustry(industry)
            binding.applyBtn.visibility = View.VISIBLE
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                DepartmentFragmentDirections
                    .actionDepartmentFilterToBaseFilter(viewModel.selectedFilter)
            )
        }
        binding.applyBtn.setOnClickListener {
            findNavController().navigate(
                DepartmentFragmentDirections
                    .actionDepartmentFilterToBaseFilter(viewModel.selectedFilter)
            )
        }
    }
    
    companion object { const val DEPARTMENT = "DepartmentFragment" }
}