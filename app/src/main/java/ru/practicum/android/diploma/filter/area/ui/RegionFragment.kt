package ru.practicum.android.diploma.filter.area.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.area.presentation.AreaScreenState
import ru.practicum.android.diploma.filter.area.presentation.AreaViewModel

class RegionFragment : Fragment() {

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AreaViewModel>()

    private var areaAdapter: AreaAdapter = AreaAdapter { area ->
        transitionToPlaceSelector(area)
    }

    private var countryId = ""
    private var countryName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterToolbarRegion.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        countryId = RegionFragmentArgs.fromBundle(requireArguments()).countryId
        countryName = RegionFragmentArgs.fromBundle(requireArguments()).countryName

        viewModel.getAreas("", countryId)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.regionRecycler.adapter = areaAdapter
        binding.regionRecycler.layoutManager = LinearLayoutManager(requireContext())

        setupListeners()

    }

    private fun render(areaScreenState: AreaScreenState) {
        when (areaScreenState) {
            is AreaScreenState.GetError -> showError(AreaScreenState.GetError)
            is AreaScreenState.EmptyError -> showError(AreaScreenState.EmptyError)
            is AreaScreenState.Content -> showContent(areaScreenState.areas)
        }
    }

    private fun showError(areaScreenState: AreaScreenState) {
        if (areaScreenState is AreaScreenState.GetError) {
            binding.errorPlaceholder.setImageResource(R.drawable.placeholder_region_response_error)
            binding.placeholderText.setText(R.string.placeholder_cannot_get_list_of_regions)
        } else {
            binding.errorPlaceholder.setImageResource(R.drawable.placeholder_nothing_found)
            binding.placeholderText.setText(R.string.placeholder_no_such_region)
        }
        binding.errorPlaceholder.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.VISIBLE
        binding.regionRecycler.visibility = View.GONE
    }

    private fun showContent(areas: ArrayList<Area>) {
        areaAdapter.areas.clear()
        areaAdapter.areas.addAll(areas)
        areaAdapter.notifyDataSetChanged()
        binding.regionRecycler.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.GONE
        binding.errorPlaceholder.visibility = View.GONE
    }

    private fun transitionToPlaceSelector(area: Area) {
        if (viewModel.clickDebounce()) {
            viewModel.saveArea(area)
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        binding.regionEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.getAreas(binding.regionEditText.text.toString(), countryId)
            if (text.isNullOrEmpty()) {
                binding.icSearchOrCross.setImageResource(R.drawable.ic_search)
            } else {
                binding.icSearchOrCross.setImageResource(R.drawable.ic_close)
            }
        }
        binding.icSearchOrCross.setOnClickListener {
            binding.regionEditText.text = null
        }
        binding.regionEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getAreas(binding.regionEditText.text.toString(), countryId)
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.regionEditText.windowToken, 0)
            }
            true
        }
    }
}
