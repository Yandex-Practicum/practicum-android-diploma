package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.presentation.ui.adapters.CountriesAdapter
import ru.practicum.android.diploma.filter.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.state.Country

internal class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private var countryClickDebounce: ((String) -> Unit)? = null

    private val listCountries: MutableList<Country> = mutableListOf()
    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private val countriesAdapter: CountriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CountriesAdapter(listCountries) {
            selectCountry(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLeftCountry.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun selectCountry(id: String) {
        countryClickDebounce?.let { it(id) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
