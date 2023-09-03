package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter

sealed interface CountryFilterScreenState{


    fun render(binding: FragmentCountryFilterBinding)

    object Empty : CountryFilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {

        }


    }
    data class Content(val list : List<Country>): CountryFilterScreenState{
        override fun render(binding: FragmentCountryFilterBinding) {
            val adapter = (binding.countyFilterRecycler.adapter as CountryFilterAdapter)
            adapter.countriesList = list
            adapter.notifyDataSetChanged()
        }

    }
}