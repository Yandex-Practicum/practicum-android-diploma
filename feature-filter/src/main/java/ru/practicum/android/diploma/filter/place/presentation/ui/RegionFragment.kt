package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.presentation.ui.adapters.PlacesAdapter
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState
import ru.practicum.android.diploma.ui.R

private const val DELAY_CLICK_ITEM = 250L
private const val INDEX_DRAWABLE_RIGHT = 2
private const val CALIBRATION_COEFFICIENT = 1.2

internal class RegionFragment : Fragment() {

    private val regionViewModel: RegionViewModel by viewModel()

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

    private var textSearchRegions = ""

    private var regionClickDebounce: ((Region) -> Unit)? = null

    private val regionAdapter: PlacesAdapter<Region> by lazy(LazyThreadSafetyMode.NONE) {
        PlacesAdapter(
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
    ): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.loadingProgressBar,
            binding.listRegions,
            binding.placeholderNoLoadList,
            binding.placeholderRegionDoesNotExist
        )
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regionViewModel.initDataFromCacheAndSp()

        initDebounce()

        regionViewModel.observePlaceState().observe(viewLifecycleOwner) {
            renderInitRegions(it)
        }

        regionViewModel.observeRegionsState().observe(viewLifecycleOwner) {
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

        binding.searchRegion.setOnTouchListener(onTouchListener)
    }

    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
            event?.let {
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEnd = binding.searchRegion.compoundDrawables[INDEX_DRAWABLE_RIGHT]
                    if (drawableEnd != null
                        && event.rawX >= (binding.searchRegion.right
                            - CALIBRATION_COEFFICIENT * drawableEnd.bounds.width())
                    ) {
                        binding.searchRegion.text.clear()
                        return true
                    }
                }
            }
            return false
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
                regionViewModel.getRegions(state.country.id)
            }

            is PlaceState.ContentPlace -> {
                state.place.idCountry?.let { regionViewModel.getRegions(it) }
            }

            is PlaceState.Empty -> {
                regionViewModel.getRegionsAll()
            }

            is PlaceState.Error -> {
                Utils.visibilityView(viewArray, binding.placeholderNoLoadList)
            }
        }
    }

    private fun initDebounce() {
        regionClickDebounce = onRegionClickDebounce {
            findNavController().navigateUp()
            regionViewModel.setPlaceInDataReserveFilter(
                Place(
                    idCountry = it.parentId,
                    nameCountry = it.parentName,
                    idRegion = it.id,
                    nameRegion = it.name
                )
            )
        }
    }

    private fun onRegionClickDebounce(action: (Region) -> Unit): ((Region) -> Unit) {
        return debounce(
            DELAY_CLICK_ITEM,
            lifecycleScope,
            useLastParam = false,
            actionThenDelay = true,
            action = action
        )
    }

    private val inputSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            textSearchRegions = oldText.toString()
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
            regionViewModel.searchDebounce(textSearchRegions)
        }

        override fun afterTextChanged(resultText: Editable?) {
            textSearchRegions = resultText.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        inputSearchWatcher.let { binding.searchRegion.removeTextChangedListener(it) }
        _binding = null
        viewArray = null
    }
}
