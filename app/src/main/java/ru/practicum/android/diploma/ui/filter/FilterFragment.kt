package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class FilterFragment : BindingFragment<FragmentFilterBinding>() {
    private val viewModel: FilterViewModel by viewModel()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiToolbar()
        // системная кн назад
        handleBackPress()

        viewModel.getAreas()

        viewModel.getIndustries()
    }

    private fun initUiToolbar() {
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForFilterScreen()
        toolbar.setToolbarTitle(getString(R.string.filter_settings))
        toolbar.setupToolbarBackButton(this)
    }
}
