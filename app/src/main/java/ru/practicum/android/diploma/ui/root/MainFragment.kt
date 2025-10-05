package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.SearchViewModel
import ru.practicum.android.diploma.ui.search.SearchState
import ru.practicum.android.diploma.ui.search.VacanciesAdapter
import ru.practicum.android.diploma.util.showToast

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private val adapter: VacanciesAdapter by lazy {
        VacanciesAdapter(
            onItemClick = { vacancy -> onVacancyClick(vacancy) }
        )
    }

    companion object {
        private const val LOAD_NEXT_PAGE_THRESHOLD = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchField()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.vacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.vacanciesRecyclerView.adapter = adapter

        binding.vacanciesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && !viewModel.isLoading() && viewModel.hasMorePages()) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (lastVisibleItemPosition >= totalItemCount - LOAD_NEXT_PAGE_THRESHOLD) {
                        println("DEBUG: Loading next page... current items: $totalItemCount")
                        viewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun setupSearchField() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                viewModel.search(s.toString())
            }
        })
    }

    private fun setupClickListeners() {
        binding.trailingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtrationFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            println("DEBUG: State changed to: ${state.javaClass.simpleName}")

            when (state) {
                is SearchState.EmptyQuery -> showEmptyQueryState()
                is SearchState.Loading -> showLoadingState()
                is SearchState.Success -> {
                    println(
                        "DEBUG: Success state - vacancies: ${state.vacancies.size}, " +
                            "isFirstPage: ${state.isFirstPage}"
                    )
                    showSuccessState(state)

                    // ВАЖНО: Обновляем адаптер для ВСЕХ успешных состояний
                    adapter.submitVacancies(state.vacancies)
                    adapter.setLoading(false)
                    adapter.setHasMore(viewModel.hasMorePages())
                }
                is SearchState.Error -> showErrorState(state.message)
                is SearchState.LoadingNextPage -> {
                    println("DEBUG: Loading next page state")
                    adapter.setLoading(true)
                    adapter.setHasMore(true)
                }
                is SearchState.NextPageError -> {
                    adapter.setLoading(false)
                    requireContext().showToast(state.message)
                }
            }
        }
    }

    private fun showEmptyQueryState() {
        binding.loadingProgressBar.isVisible = false
        binding.vacanciesRecyclerView.isVisible = false
        binding.errorStateContainer.isVisible = false
        binding.noResultsContainer.isVisible = false
        binding.emptyStateText.isVisible = true
        binding.resultsCountText.isVisible = false

        adapter.submitVacancies(emptyList())
        adapter.setLoading(false)
        adapter.setHasMore(false)
    }

    private fun showLoadingState() {
        binding.loadingProgressBar.isVisible = true
        binding.vacanciesRecyclerView.isVisible = false
        binding.errorStateContainer.isVisible = false
        binding.noResultsContainer.isVisible = false
        binding.emptyStateText.isVisible = false
        binding.resultsCountText.isVisible = false

        adapter.setLoading(false)
        adapter.setHasMore(false)
    }

    private fun showSuccessState(state: SearchState.Success) {
        binding.loadingProgressBar.isVisible = false
        binding.errorStateContainer.isVisible = false
        binding.emptyStateText.isVisible = false

        // ВАЖНО: Проверяем есть ли вакансии
        if (state.vacancies.isEmpty()) {
            // Показываем состояние "нет результатов"
            binding.vacanciesRecyclerView.isVisible = false
            binding.noResultsContainer.isVisible = true
            binding.resultsCountText.isVisible = false
        } else {
            // Показываем список вакансий
            binding.vacanciesRecyclerView.isVisible = true
            binding.noResultsContainer.isVisible = false
            showResultsCount(state.found, state.vacancies.size)
        }

        adapter.submitVacancies(state.vacancies)
        adapter.setLoading(false)
        adapter.setHasMore(viewModel.hasMorePages())

        if (state.vacancies.isNotEmpty() && state.isFirstPage) {
            requireContext().showToast("Найдено вакансий: ${state.found}")
        }
    }

    private fun showErrorState(message: String) {
        binding.loadingProgressBar.isVisible = false
        binding.vacanciesRecyclerView.isVisible = false
        binding.emptyStateText.isVisible = false
        binding.noResultsContainer.isVisible = false
        binding.errorStateContainer.isVisible = true

        // ИСПРАВЛЕНИЕ: Показываем реальное сообщение об ошибке вместо "Нет интернета"
        binding.errorStateText.text = message
        binding.resultsCountText.isVisible = false

        adapter.setLoading(false)
        adapter.setHasMore(false)

        // Можно оставить toast для отладки
        requireContext().showToast(message)
    }

    private fun showResultsCount(totalFound: Int, displayed: Int) {
        binding.resultsCountText.isVisible = totalFound > 0
        if (totalFound > 0) {
            binding.resultsCountText.text = "Найдено $totalFound вакансий"
        }
    }

    private fun onVacancyClick(vacancy: Vacancy) {
        requireContext().showToast("Открываем вакансию: ${vacancy.title}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
