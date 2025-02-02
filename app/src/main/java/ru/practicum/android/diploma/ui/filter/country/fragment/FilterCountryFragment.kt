package ru.practicum.android.diploma.ui.filter.country.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCountryBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.ui.filter.country.viewmodel.FilterCountryViewModel
import ru.practicum.android.diploma.util.FilterNames

class FilterCountryFragment : Fragment() {

    enum class UIState {
        CONTENT, LOADING, ERROR
    }

    private val binding: FragmentFilterCountryBinding by lazy { FragmentFilterCountryBinding.inflate(layoutInflater) }

    private val viewModel: FilterCountryViewModel by viewModel()

    private var adapter: CountryAdapter? = null

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

        val onClick: (String, String) -> Unit =
            { countryId: String, countryName: String -> viewModel.onItemClicked(countryId, countryName) }
        adapter = CountryAdapter(onClick)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val owner = getViewLifecycleOwner()
        viewModel.selectCountryTrigger().observe(owner) { (countryId, countryName) ->
            navigateBackWithParams(countryId, countryName)
        }

        viewModel.searchResultLiveData().observe(owner) { searchResult ->
            when (searchResult) {
                is SearchResult.Error -> showUI(UIState.ERROR, searchResult)
                is SearchResult.Loading -> showUI(UIState.LOADING, searchResult)
                is SearchResult.GetAreasContent -> showUI(UIState.CONTENT, searchResult)
                else -> {}
            }
        }
    }

    private fun navigateBackWithParams(id: String, name: String) {
        val bundle = Bundle().apply {
            putString(FilterNames.COUNTRY_ID, id)
            putString(FilterNames.COUNTRY_NAME, name)
        }
        parentFragmentManager.setFragmentResult(FilterNames.COUNTRY_RESULT, bundle)
        findNavController().popBackStack()
    }

    private fun showUI(state: UIState, searchResult: SearchResult) {
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

                val content = searchResult as SearchResult.GetAreasContent

                // помещаем "Другие регионы" в конец списка
                val countries = content.list.sortedBy {
                    it.name == getString(R.string.other_regions)
                }
                adapter?.submitList(countries)
            }
        }
    }
}
