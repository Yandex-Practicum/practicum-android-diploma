package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private val viewModel: SearchViewModel by viewModel()

    private var vacancyAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val owner = getViewLifecycleOwner()

        val onSearchTrackClick: (Vacancy) -> Unit =
            { vacancy: Vacancy -> viewModel.onVacancyClicked(vacancy) }
        vacancyAdapter = VacancyAdapter(onSearchTrackClick)
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearch.adapter = vacancyAdapter

        viewModel.searchResultLiveData()
            .observe(owner) { searchResult: SearchResult -> renderSearchResult(searchResult) }

        // нажатие на кнопку Done на клавиатуре
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editTextSearch.clearFocus()
                enterSearch()
            }
            false
        }
    }

    private fun renderSearchResult(result: SearchResult) {
        when (result) {
            is SearchResult.SearchVacanciesContent -> {
                binding.recyclerViewSearch.isVisible = true
                vacancyAdapter?.submitList(result.items)
            }

            else -> {}
        }
    }

    private fun enterSearch() {
        viewModel.searchVacancies()
    }

}
