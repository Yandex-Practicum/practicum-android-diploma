package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentFilterChooseCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.FiltersCountriesState
import ru.practicum.android.diploma.presentation.filters.FiltersCountryViewModel
import ru.practicum.android.diploma.ui.filters.recycler.FilterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.filter.ChooseIndustryFragment
import ru.practicum.android.diploma.ui.search.gone
import ru.practicum.android.diploma.ui.search.visible


class FiltersCountryFragment : Fragment() {

    private var _binding: FragmentFilterChooseCountryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltersCountryViewModel by viewModel()
    private var countriesAdapter: FilterAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterChooseCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fillData()

        viewModel.getFiltersCountriesStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        countriesAdapter = FilterAdapter { country ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(COUNTRY_KEY to country))
            findNavController().popBackStack()
        }

        binding.countryList.adapter = countriesAdapter
        binding.countryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun render(state: FiltersCountriesState) {
        when (state) {
            is FiltersCountriesState.Content -> showContent(state.countries)
            is FiltersCountriesState.Empty -> showEmpty()
            is FiltersCountriesState.Error -> showError()
            is FiltersCountriesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.progressBar.visible()
    }

    private fun showError() {
        binding.progressBar.gone()
        binding.errorFailedGet.visible()
    }

    private fun showEmpty() {
        binding.progressBar.gone()
        binding.errorFailedGet.visible()
    }

    private fun showContent(countries: List<Country>) {
        binding.progressBar.gone()
        binding.errorFailedGet.gone()
        countriesAdapter!!.countriesList.clear()
        countriesAdapter!!.countriesList.addAll(countries)
        countriesAdapter!!.notifyDataSetChanged()

    }

    companion object {
        const val REQUEST_KEY = "COUNTRY_KEY"
        const val COUNTRY_KEY = "COUNTRY"
    }
}
