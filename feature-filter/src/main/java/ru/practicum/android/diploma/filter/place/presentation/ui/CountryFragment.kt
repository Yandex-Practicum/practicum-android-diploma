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
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.PlacesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState

private const val DELAY_CLICK_COUNTRY = 250L

internal class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private var countries: Map<String, String>? = null

    private var countryClickDebounce: ((Country) -> Unit)? = null

    private val countriesAdapter: PlacesAdapter<Country> by lazy(LazyThreadSafetyMode.NONE) {
        PlacesAdapter<Country>(
            placeClickListener = {
                selectCountry(it)
            },
            itemBinder = { binding, item ->
                binding.namePlace.text = item.name
            }
        )
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
                    countries = state.countries.map { it.id to it.name }.toMap()
                }

                is CountryState.Empty -> {
                    showError()
                    regionsCountriesViewModel.setPlaceState(PlaceState.Empty)
                }

                is CountryState.Error -> {
                    showError()
                    regionsCountriesViewModel.setPlaceState(PlaceState.Error)
                }
            }
        }
    }

    private fun reset() {
        binding.rvCountryList.isVisible = false
        binding.llNoListError.isVisible = false
    }

    private fun showCountries(countries: List<Country>) {
        countriesAdapter.updatePlaces(countries)
        binding.rvCountryList.isVisible = true
    }

    private fun showError() {
        binding.llNoListError.isVisible = true
    }

    private fun selectCountry(country: Country) {
        countryClickDebounce?.let { it(country) }
    }

    private fun initDebounce() {
        countryClickDebounce = onCountryClickDebounce {
            findNavController().navigateUp()
            regionsCountriesViewModel.setPlaceInDataFilter(
                Place(
                    idCountry = it.id,
                    nameCountry = it.name,
                    idRegion = null,
                    nameRegion = null
                )
            )
        }
    }

    private fun onCountryClickDebounce(action: (Country) -> Unit): (Country) -> Unit = debounce<Country>(
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
