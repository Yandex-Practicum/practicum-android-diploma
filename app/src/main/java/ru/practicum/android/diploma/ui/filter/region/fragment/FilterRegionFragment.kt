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
import ru.practicum.android.diploma.ui.filter.country.fragment.AreaAdapter
import ru.practicum.android.diploma.ui.filter.region.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.util.FilterNames

class FilterRegionFragment : Fragment() {

    enum class UIState {
        CONTENT, LOADING, ERROR, EMPTY
    }

    private val binding: FragmentFilterRegionBinding by lazy { FragmentFilterRegionBinding.inflate(layoutInflater) }

    private val viewModel: FilterRegionViewModel by viewModel()

    private var adapter: AreaAdapter? = null

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

        val onClick: (String, String) -> Unit =
            { countryId: String, countryName: String -> viewModel.onItemClicked(countryId, countryName) }
        adapter = AreaAdapter(onClick)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val owner = getViewLifecycleOwner()
        viewModel.selectRegionTrigger().observe(owner) { (regionId, regionName) ->
            navigateBackWithParams(regionId, regionName)
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

    private fun navigateBackWithParams(id: String, name: String) {
        val bundle = Bundle().apply {
            putString(FilterNames.REGION_ID, id)
            putString(FilterNames.REGION_NAME, name)
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
                adapter?.submitList(content.others)
            }

            else -> {}
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
