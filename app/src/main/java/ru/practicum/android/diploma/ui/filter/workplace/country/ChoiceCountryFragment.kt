package ru.practicum.android.diploma.ui.filter.workplace.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentChoiceCountryBinding
import ru.practicum.android.diploma.domain.models.Country

class ChoiceCountryFragment : Fragment() {

    private var _binding: FragmentChoiceCountryBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModel<CountriesViewModel>()
    private val adapter = CountriesAdapter { selectCountry(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceCountryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListCountry.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListCountry.adapter = adapter

        viewModel.countriesState.observe(viewLifecycleOwner, ::renderState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: CountriesState) {
        when (state) {
            is CountriesState.Loading -> showLoading()
            is CountriesState.Error -> showError()
            is CountriesState.Content -> showContent(state.data)
        }
    }

    private fun selectCountry(country: Country) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_BACKSTACK_KEY, country)
        findNavController().popBackStack()
    }

    private fun showLoading() {
        binding.rvListCountry.isVisible = false
        binding.notFoundListPlaceholder.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showError() {
        binding.rvListCountry.isVisible = false
        binding.notFoundListPlaceholder.isVisible = true
        binding.progressBar.isVisible = false
    }

    private fun showContent(data: List<Country>) {
        binding.rvListCountry.isVisible = true
        binding.notFoundListPlaceholder.isVisible = false
        binding.progressBar.isVisible = false

        adapter.clear()
        adapter.setData(data)
    }

    companion object {
        const val COUNTRY_BACKSTACK_KEY = "country_key"
    }

}
