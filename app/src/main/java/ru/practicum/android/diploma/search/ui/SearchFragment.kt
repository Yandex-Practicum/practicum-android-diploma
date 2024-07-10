package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.ui.adapter.VacanciesAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private val vacanciesAdapter: VacanciesAdapter by lazy {
        VacanciesAdapter { vacancy ->

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeAdapter()

        binding.ivFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // реализация не требуется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s?.toString() ?: "")

                binding.ivClear.isVisible = !s.isNullOrEmpty()
                binding.ivSearch.isVisible = s.isNullOrEmpty()
                binding.ivPlaceholderSearch.isVisible = s.isNullOrEmpty()

                viewModel.search(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {
                // реализация не требуется
            }
        }

        textWatcher.let { binding.etSearch.addTextChangedListener(it) }

        binding.ivClear.setOnClickListener {
            binding.etSearch.setText("")
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is SearchState.Content -> showContent(screenState)
                SearchState.Error -> {
                    // реализация будет позже
                }

                SearchState.Loading -> {
                    showLoading()
                }

                SearchState.Empty -> {
                    showEmpty()
                }
            }
        }
    }

    private fun initializeAdapter() {
        binding.rvVacancies.adapter = vacanciesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(screenState: SearchState.Content) {
        vacanciesAdapter.clearItems()
        vacanciesAdapter.addItems(screenState.results)
        binding.rvVacancies.isVisible = true
        binding.numberVacancies.isVisible = true
        if (screenState.foundVacancies == 0) {
            binding.numberVacancies.text = resources.getString(R.string.search_no_vacancies)
        } else {
            binding.numberVacancies.text = "Найдено ${screenState.foundVacancies} вакансий"
        }
        binding.progressBar.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showEmpty() {
        vacanciesAdapter.clearItems()
        binding.numberVacancies.isVisible = false
        binding.progressBar.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showLoading() {
        vacanciesAdapter.clearItems()
        binding.progressBar.isVisible = true
    }
}
