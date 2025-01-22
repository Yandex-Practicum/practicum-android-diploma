package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.search.ui.decorations.LayoutItemDecoration

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var adapter: SearchAdapter? = null


    val vacancyItem = VacancyItems(
        "1",
        "Программист",
        "Москва",
        "Sber",
        null,
        Salary(
            40000,
            100000,
            "RUR"
        )
    )

    private var vacancyList = arrayListOf(vacancyItem, vacancyItem,vacancyItem,vacancyItem,vacancyItem,vacancyItem,vacancyItem,vacancyItem,vacancyItem,
        vacancyItem, vacancyItem,vacancyItem)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterSettingsFragment)
        }
        viewModel.searchVacancy("Программист")
        binding.initScreenPH.isVisible = false

        binding.searchVacanciesRV.isVisible = true

        binding.scrollView.isVisible = true
        binding.progressBar.isVisible = true
        adapter = SearchAdapter(viewModel::showVacancyDetails)
        binding.searchVacanciesRV.adapter = adapter
        binding.searchVacanciesRV.addItemDecoration(LayoutItemDecoration(86))
        binding.searchVacanciesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("OnScroll", "scroll")
                if (dy > 0) {
                    val pos =
                        (binding.searchVacanciesRV.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter!!.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached()
//                        Log.d("VacancyItems", "${vacancyList.size}")
                    }
                }
            }
        })

        viewModel.observeState().observe(viewLifecycleOwner) { state ->

            if (state is SearchViewState.Success) {
                Log.d("Details", "${state}")
                adapter?.updateItems(state.vacancyList.items)
//           render(it)
            }



        }
    }
    }
/*    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Success ->
            is SearchViewState.Loading ->
            SearchViewState.ConnectionError ->
            SearchViewState.NotFoundError ->
            SearchViewState.ServerError ->
            else -> {}
        }
    }
*/

