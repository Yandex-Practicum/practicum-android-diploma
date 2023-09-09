package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.ui.view_models.DepartmentViewModel
import ru.practicum.android.diploma.root.RootActivity

class DepartmentFragment : ChooseFragment() {
    override val fragment = DEPARTMENT
    override val viewModel: DepartmentViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun renderContent(list: List<Any?>) {
        super.renderContent(list)
        binding.toolbar.title = requireActivity().getString(R.string.choose_department)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initial()
    }

    companion object {
        const val DEPARTMENT = "DepartmentFragment"
    }
}