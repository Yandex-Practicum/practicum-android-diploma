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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterRegionBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.CountryRegionData
import ru.practicum.android.diploma.ui.filter.region.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.util.FilterNames
import ru.practicum.android.diploma.util.coroutine.CoroutineUtils

class FilterRegionFragment : Fragment() {

    private companion object {
        const val DELAY = 200L
    }

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
        viewModel.selectRegionTrigger().observe(owner) { countryRegionData: CountryRegionData ->
            navigateBackWithParams(countryRegionData)
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

                CoroutineUtils.debounce(lifecycleScope, DELAY) {
                    viewModel.filterRegions(s?.toString()?.trim())
                }
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
        countryRegionData: CountryRegionData
    ) {
        val bundle = Bundle().apply {
            putString(FilterNames.COUNTRY_ID, countryRegionData.countryId)
            putString(FilterNames.COUNTRY_NAME, countryRegionData.countryName)
            putString(FilterNames.REGION_ID, countryRegionData.regionId)
            putString(FilterNames.REGION_NAME, countryRegionData.regionName)
        }
        parentFragmentManager.setFragmentResult(FilterNames.REGION_RESULT, bundle)
        findNavController().popBackStack()
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

            is SearchResult.GetPlacesContent -> {
                if (searchResult.regions.isEmpty()) {
                    showUI(UIState.EMPTY, searchResult)
                } else {
                    showUI(
                        UIState.CONTENT,
                        searchResult
                    )
                }
            }

            else -> {}
        }
    }

    private fun showUI(
        state: UIState,
        searchResult: SearchResult
    ) {
        when (state) {
            UIState.ERROR -> {
                binding.errorPlaceholderGroup.isVisible = true
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
                binding.notFoundPlaceholderGroup.isVisible = false
            }

            UIState.LOADING -> {
                binding.errorPlaceholderGroup.isVisible = false
                binding.progressBar.isVisible = true
                binding.recyclerView.isVisible = false
                binding.notFoundPlaceholderGroup.isVisible = false
            }

            UIState.CONTENT -> {
                binding.errorPlaceholderGroup.isVisible = false
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                binding.notFoundPlaceholderGroup.isVisible = false

                val content = searchResult as SearchResult.GetPlacesContent
                adapter?.submitList(content.regions)
            }

            UIState.EMPTY -> {
                binding.errorPlaceholderGroup.isVisible = false
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
                binding.notFoundPlaceholderGroup.isVisible = true
            }
        }
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
