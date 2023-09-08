package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.models.AreasUiState
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName


open class CountryFilterFragment : AreasFragment() {

    override val fragment = COUNTRY
    override val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        initAdapterListener()
    }

    protected open fun initAdapterListener() {
        filterAdapter.onClickCountry = { country ->
            viewModel.saveCountry(country)
            findNavController().navigateUp()
        }
    }

    override fun initAdapter() {
        filterAdapter.fragment = fragment
        binding.recycler.adapter = filterAdapter
    }

    companion object { const val COUNTRY = "CountryFragment" }
}