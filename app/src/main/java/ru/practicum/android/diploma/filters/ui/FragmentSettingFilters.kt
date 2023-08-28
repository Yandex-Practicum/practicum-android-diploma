package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSettingFiltersBinding
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.util.BindingFragment

class FragmentSettingFilters:BindingFragment<FragmentSettingFiltersBinding>() {

    val viewModel by viewModel<FiltersViewModel>()


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingFiltersBinding {
        return FragmentSettingFiltersBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}