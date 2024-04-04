package ru.practicum.android.diploma.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.ui.details.DetailsFragment

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    private val vacancyAdapter by lazy {
        VacancyAdapter(onClick)
    }

    private val inputMethodManager by lazy {
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private val onClick: (Vacancy) -> Unit = {
        findNavController().navigate(
            R.id.action_searchFragment_to_fragmentDetails,
            bundleOf(DetailsFragment.vacancyIdKey to it.id)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVacancy.adapter = vacancyAdapter

        bindKeyboardSearchButton()
        bindTextWatcher()
        bindCrossButton()

        binding.searchFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterAllFragment)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Default -> showDefaultState()
            is SearchViewState.Content -> showContent(state.vacancies)
            is SearchViewState.Loading -> showLoading()
            is SearchViewState.NoInternet -> showNoInternetState()
            is SearchViewState.EmptyVacancies -> showEmptyVacanciesState()
        }
    }

    private fun showDefaultState() = with(binding) {
        ivStartSearch.isVisible = true
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false
    }

    private fun showContent(vacancies: List<Vacancy>) = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = true
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false

        vacancyAdapter.clearVacancies()
        vacancyAdapter.addVacancies(vacancies)
        vacancyAdapter.notifyDataSetChanged()
    }

    private fun showLoading() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = true
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false
    }

    private fun showNoInternetState() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = true
        nothingFoundGroup.isVisible = false
    }

    private fun showEmptyVacanciesState() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = true
    }

    private fun bindKeyboardSearchButton() {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.search.text.toString())
                true
            }
            false
        }
    }

    private fun bindTextWatcher() = with(binding) {
        search.addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                if (s.isNullOrEmpty()) {
                    ivSearch.isVisible = true
                    ivCross.isVisible = false
                } else {
                    ivSearch.isVisible = false
                    ivCross.isVisible = true
                }
            }
        )
    }

    private fun bindCrossButton() = with(binding) {
        ivCross.setOnClickListener {
            search.setText("")
            showDefaultState()
            inputMethodManager?.hideSoftInputFromWindow(
                view?.windowToken,
                0
            )
            search.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
