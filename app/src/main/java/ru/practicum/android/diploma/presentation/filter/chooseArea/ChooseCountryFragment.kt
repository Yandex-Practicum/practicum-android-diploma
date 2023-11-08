package ru.practicum.android.diploma.presentation.filter.chooseArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentChooseAreaBinding
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor.AreasAdapter
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.CountryState

class ChooseCountryFragment : Fragment() {


    private var _binding: FragmentChooseAreaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChooseCountryViewModel by viewModel()
    private var areasAdapter: AreasAdapter<Country>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chooseAreaListRecycleView
        binding.chooseAreaEnterFieldEdittext.isVisible = false
        binding.chooseAreaHeaderTextview.text = "Выбор страны"
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
        if (areasAdapter == null) {
            areasAdapter = AreasAdapter(countries) { country ->
                viewModel.onAreaClicked(country)
            }
            binding.chooseAreaListRecycleView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = areasAdapter
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
