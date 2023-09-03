package ru.practicum.android.diploma.filter.ui.models

import android.view.View
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter

sealed interface CountryFilterScreenState {


    fun render(binding: FragmentCountryFilterBinding)

    object Default: CountryFilterScreenState{
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.GONE
            binding.countyFilterRecycler.visibility = View.GONE
        }

    }
    object Empty : CountryFilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.VISIBLE
            binding.countyFilterRecycler.visibility = View.GONE
        }


    }

    data class Content(val list: List<Country>) : CountryFilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.GONE
            binding.countyFilterRecycler.visibility = View.VISIBLE
            val adapter = (binding.countyFilterRecycler.adapter as CountryFilterAdapter)
            adapter.countriesList = list
            adapter.notifyDataSetChanged()
        }

    }
}