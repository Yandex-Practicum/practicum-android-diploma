package ru.practicum.android.diploma.filter.placeselector.country.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.placeselector.country.presentation.CountryScreenState
import ru.practicum.android.diploma.filter.placeselector.country.presentation.CountryViewModel
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_ID_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY

class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountryViewModel by viewModel()
    private var countryAdapter: CountryAdapter =
        CountryAdapter { country -> transitionToPlaceSelector(country.name, country.id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.filterToolbarCountry.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.countryRecycler.adapter = countryAdapter
        binding.countryRecycler.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun render(countryScreenState: CountryScreenState) {
        when (countryScreenState) {
            is CountryScreenState.Error -> showError()
            is CountryScreenState.Content -> showContent(countryScreenState.countries)

        }
    }

    private fun showError() {
        binding.countryRecycler.isVisible = false
        binding.imageViewServerError.isVisible = true
        binding.textViewServerError.isVisible = true
    }

    private fun showContent(countries: ArrayList<Country>) {
        binding.countryRecycler.isVisible = true
        countryAdapter.countries.clear()
        countryAdapter.countries.addAll(countries)
        countryAdapter.notifyDataSetChanged()
    }

    private fun transitionToPlaceSelector(country: String, countryId: String) {
        if (viewModel.clickDebounce()) {
            val countryBundle = Bundle().apply {
                putString(COUNTRY_KEY, country)
                putString(COUNTRY_ID_KEY, countryId)
            }
            setFragmentResult(FILTER_RECEIVER_KEY, countryBundle)
            findNavController().popBackStack()
        }
    }
}
