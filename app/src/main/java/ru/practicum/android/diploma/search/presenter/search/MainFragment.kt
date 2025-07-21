package ru.practicum.android.diploma.search.presenter.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.search.presenter.model.SearchState
import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.VacancyFormatter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        VacanciesAdapter(requireContext(), mutableListOf(), ::onVacancyClick)
    }
    private val recyclerView: RecyclerView get() = binding.vacanciesRvId
    private val searchViewModel: SearchViewModel by viewModel()
    private val debouncer: Debouncer by inject { parametersOf(viewLifecycleOwner.lifecycleScope) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not implemented
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchIcon(s)
                if (!s.isNullOrBlank()) {
                    debouncer.searchDebounce {
                        searchViewModel.searchVacancies(s.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not implemented
            }
        }

        binding.editTextId.addTextChangedListener(textWatcher)
        clearEditText()
        goToFilters()
        stataObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onVacancyClick(vacancyId: Int) {
        if (debouncer.clickDebounce()) {
            findNavController().navigate(
                R.id.action_mainFragment_to_vacancyFragment,
                bundleOf("vacancyId" to vacancyId.toString())
            )
        }
    }

    private fun initRv() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun updateSearchIcon(text: CharSequence?) {
        if (text.isNullOrBlank()) {
            binding.searchIcon.setImageResource(R.drawable.search_24px)
            binding.searchIcon.tag = R.drawable.search_24px
        } else {
            binding.searchIcon.setImageResource(R.drawable.cross_light)
            binding.searchIcon.tag = R.drawable.cross_light
        }
    }

    private fun clearEditText() {
        binding.searchIcon.setOnClickListener {
            if (binding.searchIcon.tag == R.drawable.cross_light) {
                binding.editTextId.text.clear()
                debouncer.cancelDebounce()
                showEmpty()
            }
        }
    }

    private fun goToFilters() {
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtersFragment)
        }
    }

    private fun stataObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collect { state ->
                    when (state) {
                        is SearchState.Loading -> showProgressBar()
                        is SearchState.Content -> showContent(state.data)
                        is SearchState.NotFound -> showNotFound()
                        is SearchState.Error -> showError()
                        is SearchState.Empty -> showEmpty()
                    }
                }
            }
        }
    }

    private fun showContent(data: List<VacancyPreviewUi>) {
        adapter.setList(data)
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.VISIBLE
        binding.infoShieldId.visibility = View.VISIBLE
        binding.infoShieldId.text = "Найдено ${VacancyFormatter.changeEnding(data[0].found)}"
    }

    private fun showProgressBar() {
        binding.progressBarId.visibility = View.VISIBLE
        binding.searchPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
    }

    private fun showNotFound() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.VISIBLE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.VISIBLE
        binding.infoShieldId.text = "Таких вакансий нет"
    }

    private fun showError() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.VISIBLE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.VISIBLE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
    }
}
