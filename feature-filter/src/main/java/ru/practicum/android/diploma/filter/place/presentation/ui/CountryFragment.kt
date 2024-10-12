package ru.practicum.android.diploma.filter.place.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.CountriesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState

private const val DELAY_CLICK_COUNTRY = 250L
internal class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private var countryClickDebounce: ((String) -> Unit)? = null

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private val countriesAdapter: CountriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CountriesAdapter() {
            selectCountry(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDebounce()
        binding.buttonLeftCountry.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvCountryList.layoutManager = LinearLayoutManager(requireActivity(), VERTICAL, false)
        binding.rvCountryList.adapter = countriesAdapter
        reset()
        regionsCountriesViewModel.observeCountriesState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Content -> {
                    showCountries(state.countries)
                }

                else -> showError()
            }
        }
    }

    private fun reset() {
        binding.rvCountryList.isVisible = false
        binding.llNoListError.isVisible = false
    }

    private fun showCountries(countries: List<Country>) {
        countriesAdapter.countries.clear()
        countriesAdapter.countries.addAll(countries)
        countriesAdapter.notifyDataSetChanged()
        binding.rvCountryList.isVisible = true
    }

    private fun showError() {
        binding.llNoListError.isVisible = true
    }

    private fun selectCountry(id: String) {
        countryClickDebounce?.let { it(id) }
    }

    private fun initDebounce() {
        countryClickDebounce = {
            //some logic to call choosing regions
        }
    }

    private fun onCountryClickDebounce(action: (String) -> Unit): (String) -> Unit = debounce<String>(
        DELAY_CLICK_COUNTRY,
        lifecycleScope,
        false,
        false,
        action
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
