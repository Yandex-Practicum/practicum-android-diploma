package ru.practicum.android.diploma.filter.place.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.PlacesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.CountryViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState

private const val DELAY_CLICK_COUNTRY = 250L

internal class CountryFragment : Fragment() {

    private val countryViewModel: CountryViewModel by viewModel()

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

    private var countries: Map<String, String>? = null

    private var countryClickDebounce: ((Country) -> Unit)? = null

    private val countriesAdapter: PlacesAdapter<Country> by lazy(LazyThreadSafetyMode.NONE) {
        PlacesAdapter(
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
        viewArray = arrayOf(
            binding.loadingProgressBar,
            binding.rvCountryList,
            binding.llNoListError
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryViewModel.initDataFromCache()

        initDebounce()

        binding.buttonLeftCountry.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvCountryList.layoutManager = LinearLayoutManager(requireActivity(), VERTICAL, false)
        binding.rvCountryList.adapter = countriesAdapter

        countryViewModel.observeCountriesState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Loading -> {
                    Utils.visibilityView(viewArray, binding.loadingProgressBar)
                }
                is CountryState.Content -> {
                    showCountries(state.countries)
                    countries = state.countries.map { it.id to it.name }.toMap()
                }

                is CountryState.Empty -> {
                    Utils.visibilityView(viewArray, binding.llNoListError)
                }

                is CountryState.Error -> {
                    Utils.visibilityView(viewArray, binding.llNoListError)
                }
            }
        }
    }

    private fun showCountries(countries: List<Country>) {
        countriesAdapter.updatePlaces(countries)
        Utils.visibilityView(viewArray, binding.rvCountryList)
    }

    private fun selectCountry(country: Country) {
        countryClickDebounce?.let { it(country) }
    }

    private fun initDebounce() {
        countryClickDebounce = onCountryClickDebounce {
            findNavController().navigateUp()
            countryViewModel.setPlaceInDataFilterReserveBuffer(
                Place(
                    idCountry = it.id,
                    nameCountry = it.name,
                    idRegion = null,
                    nameRegion = null
                )
            )
        }
    }

    private fun onCountryClickDebounce(action: (Country) -> Unit): (Country) -> Unit = debounce(
        DELAY_CLICK_COUNTRY,
        lifecycleScope,
        useLastParam = false,
        actionThenDelay = false,
        action = action
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewArray = null
    }
}
