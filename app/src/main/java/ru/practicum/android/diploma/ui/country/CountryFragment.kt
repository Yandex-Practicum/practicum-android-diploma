package ru.practicum.android.diploma.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class CountryFragment : Fragment() {

    private val viewModel by viewModel<CountryViewModel>()
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            viewModel.setCountryInfo(
                CountryShared(
                    countryId = item.id,
                    countryName = item.name
                )
            )
            findNavController().popBackStack()
        }

        binding.countryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecycler.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Content -> {
                    adapter.countryList.addAll(state.region)
                    adapter.filteredList.addAll(state.region)
                    adapter.notifyDataSetChanged()
                }

                is CountryState.Error -> ""
                is CountryState.Loading -> ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
