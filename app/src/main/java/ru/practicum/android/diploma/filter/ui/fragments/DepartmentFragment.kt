package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.view_models.DepartmentViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName

class DepartmentFragment : ChooseFragment() {
    
    override val fragment = DEPARTMENT
    override val viewModel: DepartmentViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private var industry: Industry? = null
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.applyBtn.debounceClickListener(debouncer) {
            industry?.let { viewModel.saveIndustry(it) }
            findNavController().navigateUp()
        }
        
        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.log(thisName, "$text")
            viewModel.onSearchQueryChanged(text.toString())
        }
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
            this.industry = industry
            binding.applyBtn.visibility = View.VISIBLE
        }
    }
    
    companion object {
        const val DEPARTMENT = "DepartmentFragment"
    }
}