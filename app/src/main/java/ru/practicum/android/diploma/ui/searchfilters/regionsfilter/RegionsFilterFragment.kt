package ru.practicum.android.diploma.ui.searchfilters.regionsfilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.RegionsFilterFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.presentation.regionsfilterscreen.RegionFilterViewModel
import ru.practicum.android.diploma.presentation.regionsfilterscreen.RegionsFiltersUiState
import ru.practicum.android.diploma.ui.searchfilters.recycleview.RegionsAdapter
import ru.practicum.android.diploma.util.hideKeyboardOnDone

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

        setupInput()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(region: Region) {
        viewModel.onRegionSelected(region)
        Log.d("CountryName", region.countryName.toString())
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
                binding.ivPlaceholderCover.setImageResource(R.drawable.unable_obtain_list_placeholder)
                binding.tvPlaceholder.setText(R.string.unable_obtain_list)
            }

            is RegionsFiltersUiState.Loading -> {
                binding.progressBar.isVisible = true
                binding.rvItemList.isVisible = false
                binding.groupPlaceholder.isVisible = false
            }

            is RegionsFiltersUiState.Empty -> {
                binding.groupPlaceholder.isVisible = true
                binding.progressBar.isVisible = false
                binding.rvItemList.isVisible = false
            }
        }
    }

    private fun setupInput() {
        binding.inputEditText.doOnTextChanged { text, start, before, count ->
            val query = text?.toString()?.trim().orEmpty()

            if (query.isNotEmpty() && binding.inputEditText.hasFocus()) {
                binding.icon.setImageResource(R.drawable.close_24px)
                binding.icon.setOnClickListener {
                    binding.inputEditText.setText("")
                }
            } else {
                binding.icon.setImageResource(R.drawable.search_24px)
            }
            viewModel.search(query)
        }

        binding.inputEditText.hideKeyboardOnDone(requireContext())
    }
}
