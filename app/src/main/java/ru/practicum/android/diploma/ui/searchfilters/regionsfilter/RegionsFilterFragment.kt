package ru.practicum.android.diploma.ui.searchfilters.regionsfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.RegionsFilterFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.domain.models.filters.SelectionType
import ru.practicum.android.diploma.presentation.regionsfilterscreen.RegionFilterViewModel
import ru.practicum.android.diploma.presentation.regionsfilterscreen.RegionsFiltersUiState
import ru.practicum.android.diploma.ui.searchfilters.recycleview.RegionsAdapter
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.COUNTRY_NAME_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.REGION_NAME_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.SELECTION_RESULT_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.SELECTION_TYPE_KEY

class RegionsFilterFragment : Fragment(), RegionsAdapter.OnClickListener {
    private var _binding: RegionsFilterFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = RegionsAdapter(this)

    private val viewModel by viewModel<RegionFilterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RegionsFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemList.adapter = adapter

        viewModel.getRegionState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(region: Region) {
        val result = Bundle().apply {
            putString(SELECTION_TYPE_KEY, SelectionType.REGION.value)
            putString(COUNTRY_NAME_KEY, region.countryName)
            putString(REGION_NAME_KEY, region.name)
        }
        setFragmentResult(SELECTION_RESULT_KEY, result)
        findNavController().popBackStack()
    }

    private fun render(state: RegionsFiltersUiState) {
        when (state) {
            is RegionsFiltersUiState.Content -> {
                binding.rvItemList.isVisible = true
                adapter.submitList(state.regions)
                binding.progressBar.isVisible = false
                binding.groupPlaceholder.isVisible = false
            }
            is RegionsFiltersUiState.Error -> {
                binding.groupPlaceholder.isVisible = true
                binding.progressBar.isVisible = false
                binding.rvItemList.isVisible = false
            }

            RegionsFiltersUiState.Loading -> {
                binding.progressBar.isVisible = true
                binding.rvItemList.isVisible = false
                binding.groupPlaceholder.isVisible = false
            }
        }
    }
}
