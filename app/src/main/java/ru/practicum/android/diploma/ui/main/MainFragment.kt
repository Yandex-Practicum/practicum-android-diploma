package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.main.adapters.SearchAdapter
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment
import ru.practicum.android.diploma.ui.vacancy.model.VacancySearchState
import ru.practicum.android.diploma.ui.main.utils.VacancyCallback
import ru.practicum.android.diploma.util.debounce

class MainFragment : BindingFragment<FragmentMainBinding>() {
    private val viewModel: MainViewModel by viewModel()
    private var searchAdapter: SearchAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onVacancyClickDebounce = debounce<Vacancy>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy -> clickToVacancy(vacancy) }

        searchAdapter = SearchAdapter(
            object : SearchAdapter.VacancyClickListener {
                override fun onVacancyClick(vacancy: Vacancy) {
                    onVacancyClickDebounce(vacancy)
                }
            }
        )
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.testSearch.setOnClickListener {
            viewModel.searchVacancies("Java разработчик")
        }
        binding.searchingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchingList.adapter = searchAdapter
        binding.searchingList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.searchingList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemCount = searchAdapter?.itemCount ?: 0
                    if (pos >= itemCount - 1) {
                        viewModel.nextSearch()
                    }
                }
            }
        })
    }

    private fun render(vacanciesState: VacancySearchState) {
        when (vacanciesState) {
            is VacancySearchState.Content -> showContent(vacanciesState.vacancies)
            is VacancySearchState.Empty -> showEmpty(vacanciesState.message)
            is VacancySearchState.Error -> showError(vacanciesState.errorMessage)
            is VacancySearchState.Loading -> loading()
        }
    }

    private fun loading() {
        binding.loader.isVisible = true
        enabledControls(false)
    }

    private fun showEmpty(message: String) {
        binding.loader.isVisible = false
        enabledControls(true)
        Log.i("VACANCY_TEST", "Empty $message")
    }

    private fun showError(message: String) {
        binding.loader.isVisible = false
        enabledControls(true)
        Log.e("VACANCY_TEST", "Error $message")
    }

    private fun showContent(vacanciesList: List<Vacancy>) {
        binding.loader.isVisible = false
        enabledControls(true)
        searchAdapter?.let {
            val diffVacancyCallback = VacancyCallback(it.vacancies.toList(), vacanciesList)
            val diffVacancy = DiffUtil.calculateDiff(diffVacancyCallback)
            it.vacancies.clear()
            it.vacancies.addAll(vacanciesList)
            diffVacancy.dispatchUpdatesTo(it)
        }
    }

    private fun clickToVacancy(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_mainFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancy.id)
        )
    }

    private fun enabledControls(enable: Boolean) {
        for (i in 0..<binding.main.childCount) {
            val child: View = binding.main.getChildAt(i)
            child.setEnabled(enable)
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
