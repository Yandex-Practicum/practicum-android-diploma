package ru.practicum.android.diploma.ui.filter.place.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.ui.filter.place.country.adapters.CountryAdapter
import ru.practicum.android.diploma.ui.filter.place.models.CountryState
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.handleBackPress

class CountryFilterFragment : BindingFragment<FragmentCountryFilterBinding>() {
    private val viewModel: CountryViewModel by viewModel()

    private var adapter: CountryAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountryFilterBinding {
        return FragmentCountryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // системная кн назад
        handleBackPress()

        val onClickCountryDebounce = debounce<Areas>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) {
            country -> onClickCountry(country)
        }

        adapter = CountryAdapter(
            object : CountryAdapter.CountryClickListener {
                override fun onCountryClick(country: Areas) {
                    onClickCountryDebounce(country)
                }
            }
        )

        binding.countryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecyclerView.adapter = adapter

        viewModel.observeState.observe(viewLifecycleOwner) {
            when (it) {
                is CountryState.Content -> showCountries(it.countries)
                is CountryState.Empty -> showEmpty()
                is CountryState.Error -> showError(it.error)
                is CountryState.Loading -> showLoading()
            }
        }
    }

    private fun onClickCountry(country: Areas) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showCountries(countries: List<Areas>) {
        adapter?.countries?.clear()
        adapter?.countries?.addAll(countries)
        adapter?.notifyDataSetChanged()
        binding.includedProgressBar.progressBar.isVisible = false
        binding.countryRecyclerView.isVisible = true
    }

    private fun showEmpty() {

    }

    private fun showError(error: Int) {

    }

    private fun showLoading() {
        binding.includedProgressBar.progressBar.isVisible = true
        binding.countryRecyclerView.isVisible = false
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
