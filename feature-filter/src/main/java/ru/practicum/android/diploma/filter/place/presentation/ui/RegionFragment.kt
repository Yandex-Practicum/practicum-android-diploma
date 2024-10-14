package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.filter.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.PlacesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState

private const val DELAY_CLICK_ITEM = 250L
internal class RegionFragment : Fragment() {

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

    private var textSearchRegions = ""

    private var regionClickDebounce: ((Region) -> Unit)? = null

    private val regionAdapter: PlacesAdapter<Region> by lazy(LazyThreadSafetyMode.NONE) {
        PlacesAdapter<Region>(
            placeClickListener = {
                regionSelection(it)
            },
            itemBinder = { binding, item ->
                binding.namePlace.text = item.name
            }
        )
    }

    private fun regionSelection(region: Region) {
        regionClickDebounce?.let { it(region) }
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

        initDebounces()

        regionsCountriesViewModel.observePlaceState().observe(viewLifecycleOwner) {
            renderInitRegions(it)
        }

        regionsCountriesViewModel.observeRegionsState().observe(viewLifecycleOwner) {
            renderViewRegions(it)
        }

        binding.listRegions.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.listRegions.adapter = regionAdapter
        regionAdapter.notifyDataSetChanged()

        binding.searchRegion.addTextChangedListener(inputSearchWatcher)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderViewRegions(state: RegionState) {
        Utils.visibilityView(viewArray, binding.loadingProgressBar)
        when (state) {
            is RegionState.Loading -> {
                Utils.visibilityView(viewArray, binding.loadingProgressBar)
            }

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

    private fun renderInitRegions(state: PlaceState) {
        when (state) {
            is PlaceState.ContentCountry -> {
                regionsCountriesViewModel.getRegions(state.country.id)
            }

            is PlaceState.ContentPlace -> {
                regionsCountriesViewModel.getRegions(state.place.idCountry)
            }

            is PlaceState.Empty -> {
                regionsCountriesViewModel.getRegionsAll()
            }

            is PlaceState.Error -> {
                Utils.visibilityView(viewArray, binding.placeholderNoLoadList)
            }
        }
    }

    private fun initDebounces() {
        regionClickDebounce = onRegionClickDebounce {
            findNavController().navigate(
                ru.practicum.android.diploma.filter.R.id.action_regionFragment_to_placeFragment,
                PlaceFragment.createArgsRegion(it.parentId, it.parentName, it.id, it.name)
            )
        }
    }

    private fun onRegionClickDebounce(action: (Region) -> Unit): ((Region) -> Unit)? {
        return debounce<Region>(
            DELAY_CLICK_ITEM,
            lifecycleScope,
            false,
            true,
            action
        )
    }

    private val inputSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onTextChanged(inputText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val isInputText = !inputText.isNullOrEmpty()
            val searchStatusDrawable = if (isInputText) {
                R.drawable.clear
            } else {
                R.drawable.search_loupe
            }
            binding.searchRegion.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                searchStatusDrawable,
                0
            )
            textSearchRegions = inputText.toString()
            regionsCountriesViewModel.searchDebounce(textSearchRegions)
        }

        override fun afterTextChanged(resultText: Editable?) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        inputSearchWatcher.let { binding.searchRegion.removeTextChangedListener(it) }
        _binding = null
        viewArray = null
    }
}
