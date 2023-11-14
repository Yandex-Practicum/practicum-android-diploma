package ru.practicum.android.diploma.presentation.filter.selectArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectAreaBinding
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.CountryAdapter
import ru.practicum.android.diploma.presentation.filter.selectArea.state.CountryState

class SelectCountryFragment : Fragment() {


    private var _binding: FragmentSelectAreaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectCountryViewModel by viewModel()
    private var countryAdapter: CountryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chooseAreaListRecycleView
        binding.chooseAreaEnterFieldEdittext.isVisible = false
        binding.chooseAreaHeaderTextview.text = getString(R.string.selectionCountries)
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Display -> displayCountries(state.content)
                is CountryState.Error -> displayError(state.errorText)
            }
        }
        viewModel.getCountries()
        binding.chooseAreaBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun displayCountries(countries: List<Country>) {
        binding.apply {
            chooseAreaListRecycleView.visibility = View.VISIBLE
            errorAreasLayout.visibility = View.GONE
        }
        if (countryAdapter == null) {
            countryAdapter = CountryAdapter(countries) { country ->
                viewModel.onAreaClicked(country)
                findNavController().popBackStack()
            }

            binding.chooseAreaListRecycleView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = countryAdapter
            }
        } else {
            //todo
        }
    }

    private fun displayError(errorText: String) {
        binding.apply {
            chooseAreaListRecycleView.visibility = View.INVISIBLE
            errorAreasLayout.visibility = View.VISIBLE
            areasErrorText.text = errorText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
