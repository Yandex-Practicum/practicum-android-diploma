package ru.practicum.android.diploma.ui.filter.place.region

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region
import ru.practicum.android.diploma.ui.filter.place.models.RegionState
import ru.practicum.android.diploma.ui.filter.place.region.adapters.RegionListCallback
import ru.practicum.android.diploma.ui.filter.place.region.adapters.RegionsAdapter
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.COUNTRY_KEY
import ru.practicum.android.diploma.util.REGION_KEY
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.getSerializable

class RegionFilterFragment : BindingFragment<FragmentRegionFilterBinding>() {
    private val regionViewModel: RegionViewModel by viewModel {
        parametersOf(
            getSerializable(requireArguments(), ARG_COUNTRY, Country::class.java),
        )
    }

    private var adapter: RegionsAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegionFilterBinding {
        return FragmentRegionFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // подсказка Введите регион
        binding.regionSearch.searchEditText.hint = getString(R.string.enter_region)

        regionViewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is RegionState.Content -> showContent(it.regions)
                is RegionState.Empty -> showEmpty()
                is RegionState.Error -> showError()
                is RegionState.Loading -> showLoading()
                is RegionState.NotFound -> showNotFound()
            }
        }

        val onClickRegionDebounce = debounce<Region>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { region ->
            onClickRegion(region)
        }

        adapter = RegionsAdapter(
            object : RegionsAdapter.RegionClickListener {
                override fun onCountryClick(region: Region) {
                    onClickRegionDebounce(region)
                }
            }
        )
        binding.regionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecyclerView.adapter = adapter

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                return
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                regionViewModel.onFiltered(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        }
        binding.regionSearch.searchEditText.addTextChangedListener(textWatcher)

        // Системная кнопка или жест назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(false)
        }

        initUiTopbar()
    }

    private fun showContent(regions: List<Region>) {
        binding.includedProgressBar.progressBar.isVisible = false
        binding.placeholder.isVisible = false
        adapter?.let {
            val diffRegionsCallback = RegionListCallback(it.regions, regions)
            val diffRegions = DiffUtil.calculateDiff(diffRegionsCallback)
            it.regions.clear()
            it.regions.addAll(regions)
            diffRegions.dispatchUpdatesTo(it)
        }
        binding.regionRecyclerView.isVisible = true
    }

    private fun showLoading() {
        binding.includedProgressBar.progressBar.isVisible = true
        binding.placeholder.isVisible = false
        binding.regionRecyclerView.isVisible = false
    }

    private fun showError() {
        binding.includedProgressBar.progressBar.isVisible = false
        binding.regionRecyclerView.isVisible = false
        loadPlaceholder(R.drawable.err_server_1, R.string.server_error)
        binding.placeholder.isVisible = true
    }

    private fun showNotFound() {
        binding.includedProgressBar.progressBar.isVisible = false
        binding.regionRecyclerView.isVisible = false
        loadPlaceholder(R.drawable.err_wtf_cat, R.string.no_region)
        binding.placeholder.isVisible = true
    }

    private fun showEmpty() {
        binding.includedProgressBar.progressBar.isVisible = false
        binding.regionRecyclerView.isVisible = false
        loadPlaceholder(R.drawable.err_load_list, R.string.no_get_list)
        binding.placeholder.isVisible = true
    }

    private fun loadPlaceholder(resourceIdImage: Int, resourceIdText: Int) {
        Glide.with(requireContext())
            .load(resourceIdImage)
            .placeholder(R.drawable.err_empty_list)
            .into(binding.placeholderImg)
        binding.placeholderText.text = resources.getString(resourceIdText)
    }

    private fun onClickRegion(region: Region) {
        if (region.country != null) {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_KEY, region.country)
        }
        findNavController().previousBackStackEntry?.savedStateHandle?.set(REGION_KEY, region)
        findNavController().popBackStack()
    }

    private fun initUiTopbar() {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.region)
        }

        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(false)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()
    }

    companion object {
        private const val ARG_COUNTRY = "country"
        private const val CLICK_DEBOUNCE_DELAY = 500L

        fun createArgs(country: Country?): Bundle = bundleOf(
            ARG_COUNTRY to country
        )
    }
}
