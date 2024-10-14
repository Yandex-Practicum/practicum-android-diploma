package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.filter.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.PlacesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.SelectedCountryState

internal class RegionFragment : Fragment() {

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

//    private var regions: MutableList<Region> = mutableListOf()

    private val regionAdapter: PlacesAdapter<Region> by lazy(LazyThreadSafetyMode.NONE) {
        PlacesAdapter<Region>(
            placeClickListener = {
                // regionSelection(it)
            },
            itemBinder = { binding, item ->
                binding.namePlace.text = item.name
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.loadingProgressBar,
            binding.listRegions,
            binding.placeholderNoLoadList,
            binding.placeholderRegionDoesNotExist
        )
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regionsCountriesViewModel.observeSelectedCountryState().observe(viewLifecycleOwner) {
            renderInitRegions(it)
        }

        regionsCountriesViewModel.observeRegionsState().observe(viewLifecycleOwner) {
            renderViewRegions(it)
        }

        binding.listRegions.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.listRegions.adapter = regionAdapter
        regionAdapter.notifyDataSetChanged()

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderViewRegions(state: RegionState) {
        Utils.visibilityView(viewArray, binding.loadingProgressBar)
        when (state) {
            is RegionState.Content -> {
                regionAdapter.updatePlaces(state.regions)
                Utils.visibilityView(viewArray, binding.listRegions)
            }

            is RegionState.Empty -> {
                regionAdapter.updatePlaces(emptyList())
                Utils.visibilityView(viewArray, binding.placeholderRegionDoesNotExist)
            }

            is RegionState.Error -> {
                regionAdapter.updatePlaces(emptyList())
                Utils.visibilityView(viewArray, binding.placeholderNoLoadList)
            }
        }
    }

    private fun renderInitRegions(state: SelectedCountryState) {
        when (state) {
            is SelectedCountryState.SelectedCountry -> {
                regionsCountriesViewModel.getRegions(state.country.id)
            }

            is SelectedCountryState.Empty -> {
                regionsCountriesViewModel.getRegionsAll()
            }

            is SelectedCountryState.Error -> {
                Utils.visibilityView(viewArray, binding.placeholderNoLoadList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewArray = null
    }
}
