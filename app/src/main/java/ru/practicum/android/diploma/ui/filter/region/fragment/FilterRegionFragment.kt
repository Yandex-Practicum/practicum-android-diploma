package ru.practicum.android.diploma.ui.filter.region.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterRegionBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.filter.region.viewmodel.CountryRegionData
import ru.practicum.android.diploma.ui.filter.region.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.util.FilterNames

class FilterRegionFragment : Fragment() {

    enum class UIState {
        CONTENT, LOADING, ERROR, EMPTY
    }

    private val binding: FragmentFilterRegionBinding by lazy { FragmentFilterRegionBinding.inflate(layoutInflater) }

    private val viewModel: FilterRegionViewModel by viewModel()

    private var adapter: RegionAdapter? = null

    private var countryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        arguments?.let {
            countryId = it.getString(FilterNames.COUNTRY_ID)
        }

        viewModel.searchRegions(countryId)

        val onClick: (CountryRegionData) -> Unit =
            { countryRegionData ->
                viewModel.onItemClicked(
                    countryRegionData.countryId,
                    countryRegionData.countryName,
                    countryRegionData.regionId,
                    countryRegionData.regionName,
                )
            }
        adapter = RegionAdapter(onClick)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val owner = getViewLifecycleOwner()
        viewModel.selectRegionTrigger().observe(owner) { (countryId, countryName, regionId, regionName) ->
            navigateBackWithParams(countryId, countryName, regionId, regionName)
        }

        // очистить строку поиска
        binding.clearIcon.setOnClickListener {
            clearSearchText()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = clearButtonIsVisible(s)
                binding.searchIcon.isVisible = !clearButtonIsVisible(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        binding.editTextSearch.addTextChangedListener(simpleTextWatcher)

        viewModel.searchResultLiveData().observe(owner) { searchResult ->
            setStates(searchResult)
        }
    }

    private fun navigateBackWithParams(
        countryId: String, countryName: String,
        regionId: String, regionName: String
    ) {
        val bundle = Bundle().apply {
            putString(FilterNames.COUNTRY_ID, countryId)
            putString(FilterNames.COUNTRY_NAME, countryName)
            putString(FilterNames.REGION_ID, regionId)
            putString(FilterNames.REGION_NAME, regionName)
        }
        findNavController().navigate(
            R.id.action_filterRegionFragment_to_filterCountryRegionFragment,
            bundle,
        )
    }

    private fun setStates(searchResult: SearchResult) {
        when (searchResult) {
            is SearchResult.Error -> showUI(
                UIState.ERROR,
                searchResult
            )

            is SearchResult.Loading -> showUI(
                UIState.LOADING,
                searchResult
            )

            is SearchResult.GetPlacesContent -> showUI(
                UIState.CONTENT,
                searchResult
            )

            else -> {}
        }
    }

    private fun showUI(
        state: UIState,
        searchResult: SearchResult
    ) {
        binding.notFoundPlaceholderGroup.isVisible = false

        when (state) {
            UIState.ERROR -> {
                binding.errorPlaceholderGroup.isVisible = true
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
            }

            UIState.LOADING -> {
                binding.errorPlaceholderGroup.isVisible = false
                binding.progressBar.isVisible = true
                binding.recyclerView.isVisible = false
            }

            UIState.CONTENT -> {
                binding.errorPlaceholderGroup.isVisible = false
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                val content = searchResult as SearchResult.GetPlacesContent

                val regionMap = content.others.associateBy { it.id } + content.countries.associateBy { it.id }
                val list = content.others.map { Pair(it, findCountry(it, regionMap)) }.map {
                    CountryRegionData(
                        it.second.id,
                        it.second.name!!,
                        it.first.id,
                        it.first.name!!
                    )
                }
                adapter?.submitList(list)
            }

            else -> {}
        }
    }

    private fun findCountry(region: Area, areas: Map<String, Area>): Area {
        var area = region
        while (area.parentId != null) {
            area = areas[area.parentId]!!
        }
        return area
    }

    private fun clearSearchText() {
        binding.editTextSearch.setText("")
        val view: View? = requireActivity().currentFocus

        if (view != null) {
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding.editTextSearch.clearFocus()
    }

    private fun clearButtonIsVisible(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }
}
