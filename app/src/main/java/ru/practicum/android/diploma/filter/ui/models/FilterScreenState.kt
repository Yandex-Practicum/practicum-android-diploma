package ru.practicum.android.diploma.filter.ui.models

import android.view.View
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter

sealed interface FilterScreenState {


    fun render(binding: FragmentCountryFilterBinding)

    object Default: FilterScreenState{
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.GONE
            binding.countyFilterRecycler.visibility = View.GONE
        }

    }
    object Loading: FilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            //TODO ProgressBar
        }
    }

    class NoData(val list: List<Country>) : FilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.VISIBLE
            binding.countyFilterRecycler.visibility = View.GONE
        }
    }

    data class Content(val list: List<Country>) : FilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            binding.countryPlaceholderContainer.visibility = View.GONE
            binding.countyFilterRecycler.visibility = View.VISIBLE
            val adapter = (binding.countyFilterRecycler.adapter as CountryFilterAdapter)
            adapter.countriesList = list
            adapter.notifyDataSetChanged()
        }

    }

    class Error(val message: String): FilterScreenState {
        override fun render(binding: FragmentCountryFilterBinding) {
            //TODO Toast
        }
    }
}