package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter
import ru.practicum.android.diploma.filter.ui.models.CountryFilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


class CountryFilterFragment : Fragment(R.layout.fragment_country_filter) {
    private val binding by viewBinding<FragmentCountryFilterBinding>()

    @Inject lateinit var countryAdapter: CountryFilterAdapter
    private val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initAdapter()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { screenState ->
                viewModel.log(thisName, "screenState ${screenState.thisName}")
                when (screenState) {
                    is CountryFilterScreenState.Empty -> {
                        screenState.render(binding)
                    }

                    is CountryFilterScreenState.Content -> {
                        screenState.render(binding)
                    }

                    CountryFilterScreenState.Default -> {
                        screenState.render(binding)
                    }
                }

            }
        }

    }

    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        countryAdapter.onItemClick = {

        }
    }
    private fun initAdapter() {
        binding.countyFilterRecycler.adapter = countryAdapter
    }

}