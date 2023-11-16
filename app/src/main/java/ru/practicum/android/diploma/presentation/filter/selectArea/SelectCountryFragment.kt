package ru.practicum.android.diploma.presentation.filter.selectArea

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.ModelFragment
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.CountryAdapter
import ru.practicum.android.diploma.presentation.filter.selectArea.state.CountryState

class SelectCountryFragment : ModelFragment() {

    private val viewModel: SelectCountryViewModel by viewModel()
    private var countryAdapter: CountryAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarInclude.headerTitle.text = getString(R.string.select_countries)
        binding.container.isVisible = false
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Display -> displayCountries(state.content)
                is CountryState.Error -> displayError(state.errorText)
                else -> {}
            }
        }
        viewModel.getCountries()
    }


    private fun displayCountries(countries: List<Country>) {
        binding.apply {
            RecyclerView.visibility = View.VISIBLE
            placeholderMessage.visibility = View.GONE
        }
        if (countryAdapter == null) {
            countryAdapter = CountryAdapter(countries) { country ->
                viewModel.onClicked(country)
                findNavController().popBackStack()
            }

            binding.RecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = countryAdapter
            }
        }
    }

    private fun displayError(errorText: String) {
        binding.apply {
            RecyclerView.visibility = View.INVISIBLE
            placeholderMessage.visibility = View.VISIBLE
            placeholderMessageImage.setImageResource(R.drawable.search_placeholder_nothing_found)
            placeholderMessageText.text = errorText
        }
    }
}
