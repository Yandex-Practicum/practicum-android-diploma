package ru.practicum.android.diploma.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.converters.AreaConverter.mapToCountry
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.ui.country.CountryAdapter

class RegionFragment : Fragment() {

    private val viewModel by viewModel<RegionViewModel>()
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("keyRegion", item.name)
            setFragmentResult("requestKeyRegion", bundle)
            findNavController().popBackStack()
        }

        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter

        viewModel.loadRegion("113")

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Content -> {
                    adapter.countryList.addAll(state.regionId.areas.map { it.mapToCountry() }.sortedBy { it.name })
                    adapter.notifyDataSetChanged()
                }

                is RegionState.Error -> ""
                is RegionState.Loading -> ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
