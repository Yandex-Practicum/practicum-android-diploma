package ru.practicum.android.diploma.presentation.ui.search

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.presentation.adapter.VacancyAdapter

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter = VacancyAdapter()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.iconSearchField.setImageResource(R.drawable.del_search_string_icon)
                } else {
                    binding.iconSearchField.setImageResource(R.drawable.search_icon_search_fragment)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    viewModel.searchDebounce(s.toString())
                }
            }
        })
        binding.apply {
            recyclerView.adapter = searchAdapter
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderScreen(state)
        }
        binding.iconSearchField.setOnClickListener { binding.searchField.setText(getString(R.string.empty_string)) }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = searchAdapter.itemCount
                    if (pos >= itemsCount-1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
    }

    private fun renderScreen(state: SearchScreenState) {
        clearScreen()
        when (state) {
            is SearchScreenState.ServerError -> {
                Log.d("mytag", "SearchScreenState.ServerError $state")
            }

            is SearchScreenState.Loading->{
                clearScreen()
                binding.progressBar.isVisible = true
            }

            is SearchScreenState.ShowVacancies -> {
                clearScreen()
                binding.searchResult.text= state.page.found.toString()
                binding.searchResult.isVisible=true
                binding.recyclerView.isVisible = true
                searchAdapter.updateItems(state.page.vacancies)
                searchAdapter.notifyDataSetChanged()
            }
            is SearchScreenState.LoadNextPage -> {
                binding.progressBar.isVisible = true
            }
            else -> {Log.d("mytag", "branch else state:$state")}
        }
    }

    private fun clearScreen() {
        binding.apply {
            progressBar.isVisible = false
            searchCentralPlaceholderImg.isVisible = false
            errorMessagePlaceholder.isVisible = false
            searchResult.isVisible = false
            recyclerView.isVisible = false
        }
    }


}
