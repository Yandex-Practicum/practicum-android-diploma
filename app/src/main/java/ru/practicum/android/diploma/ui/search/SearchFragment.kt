package ru.practicum.android.diploma.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter


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
        setupAdapter()
        setupUI()
        setupObservers()
    }

    private fun setupAdapter() {
        val onSearchVacancyDebounce = debounce<Vacancy>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = lifecycleScope,
            false
        ) { vacancy ->
            val action = SearchFragmentDirections.actionSearchToVacancyDetails(vacancy.id)
            findNavController().navigate(action)
        }

        adapter = SearchAdapter(onSearchVacancyDebounce)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupUI() {
        binding.includeToolbar.toolbar.contentInsetStartWithNavigation =
            resources.getDimensionPixelSize(R.dimen.indent_16)
        binding.includeToolbar.toolbar.title = getString(R.string.search_vacancies)
        binding.includeToolbar.btnBack.visibility = View.GONE
        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(R.id.action_search_to_filter)
        }

        binding.searchVacancy.doOnTextChanged { text, _, _, _ ->
            val drawable = if (text.isNullOrEmpty()) {
                R.drawable.ic_search_24
            } else {
                R.drawable.ic_clear
            }
            binding.buttonSearchClear.setImageResource(drawable)
            viewModel.searchDebounce(text.toString())
        }

        binding.searchVacancy.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(binding.searchVacancy.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.buttonSearchClear.setOnClickListener {
            if (binding.searchVacancy.text.isNotEmpty()) {
                binding.searchVacancy.text.clear()
            }
        }
    }

    private fun setupObservers() {
        viewModel.searchStateLiveData.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: SearchState) = with(binding) {
        when (state) {
            is SearchState.Initial -> {
                textImageCaption.isVisible = false
                resultSearchInformation.isVisible = false
                recyclerView.isVisible = false
                progressBar1.isVisible = false
                progressBar2.isVisible = false
                placeholderSearch.setImageResource(R.drawable.img_empty_search)
                placeholderSearch.isVisible = true
            }

            is SearchState.Loading -> {
                placeholderSearch.isVisible = false
                textImageCaption.isVisible = false
                resultSearchInformation.isVisible = false
                recyclerView.isVisible = false
                progressBar2.isVisible = false
                progressBar1.isVisible = true
            }

            is SearchState.Content -> {
                hideKeyboard()
                val count = state.totalFound
                resultSearchInformation.text = context?.resources?.getQuantityString(
                    R.plurals.vacancies_found,
                    count,
                    count
                )
                placeholderSearch.isVisible = false
                textImageCaption.isVisible = false
                progressBar1.isVisible = false
                progressBar2.isVisible = false
                resultSearchInformation.isVisible = true
                recyclerView.isVisible = true
                adapter.setVacancies(state.vacancies)
            }

            is SearchState.Empty -> {
                resultSearchInformation.isVisible = false
                recyclerView.isVisible = false
                progressBar1.isVisible = false
                progressBar2.isVisible = false
                placeholderSearch.setImageResource(R.drawable.img_nothing_found)
                placeholderSearch.isVisible = true
                textImageCaption.text = getString(R.string.error_unable_to_retr_vac_list)
                textImageCaption.isVisible = true
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchVacancy.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
