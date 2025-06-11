package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.vacancy.adapters.VacancyAdapter
import ru.practicum.android.diploma.ui.vacancy.model.VacancySearchState
import ru.practicum.android.diploma.ui.vacancy.utils.VacancyCallback
import ru.practicum.android.diploma.util.debounce

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {
    private val viewModel: VacancyViewModel by viewModel()

    private var searchAdapter: VacancyAdapter? = null
    private lateinit var onVacancyClickDebounce: (Vacancy) -> Unit

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVacancyClickDebounce = debounce<Vacancy> (
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy -> clickToVacancy(vacancy)}

        searchAdapter = VacancyAdapter(
            object : VacancyAdapter.VacancyClickListener {
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
            binding.testNext.isVisible = true
        }
        binding.testNext.setOnClickListener {
            viewModel.nextSearch()
        }
        binding.searchingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchingList.adapter = searchAdapter
    }

    private fun render(vacanciesState: VacancySearchState) {
        when(vacanciesState) {
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

    }

    private fun enabledControls(enable: Boolean) {
        for (i in 0..< binding.main.childCount) {
            val child: View = binding.main.getChildAt(i)
            child.setEnabled(enable)
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
