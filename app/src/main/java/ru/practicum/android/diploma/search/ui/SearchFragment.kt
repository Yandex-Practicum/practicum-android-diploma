package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.details.presentation.ui.VacancyFragment
import ru.practicum.android.diploma.search.domain.SearchState
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.adapter.VacancyAdapter
import ru.practicum.android.diploma.util.debounce


class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    lateinit var vacancy: Vacancy
    private lateinit var adapter: VacancyAdapter
    private lateinit var onVacancyClickDebounce: (Vacancy) -> Unit

    private lateinit var vacancySearchDebounce: (String) -> Unit

    private val viewModel by viewModel<SearchViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(requireActivity()) { render(it) }
        adapter = VacancyAdapter(ArrayList<Vacancy>())
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.iconStateLiveData.observe(viewLifecycleOwner) { state ->
            changeIconInEditText(state)
        }

        listener()
    }

    private fun showVacanciesList(vacancies: List<Vacancy>, foundValue: Int) {

        binding.searchResult.visibility = View.VISIBLE
        binding.searchResult.text = "Найдено $foundValue вакансий"
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE

        hideKeyBoard()
        adapter.setVacancies(vacancies)
        adapter.notifyDataSetChanged()

        binding.searchEditText.clearFocus()
    }

    private fun showError(errorMessage: String) {
        binding.searchResult.text = errorMessage
        binding.searchResult.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE
    }

    private fun showEmpty(emptyMessage: String) {
        binding.searchResult.text = emptyMessage
        binding.searchResult.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE
    }

    private fun changeIconInEditText(state: IconState) {
        when (state) {
            IconState.CloseIcon -> setCloseIconForEditText()
            IconState.SearchIcon -> setSearchIconForEditText()
        }
    }

    private fun setCloseIconForEditText() {
        binding.editTextSearchImage.visibility = View.GONE
        binding.editTextCloseImage.visibility = View.VISIBLE
    }

    private fun setSearchIconForEditText() {
        binding.editTextCloseImage.visibility = View.GONE
        binding.editTextSearchImage.visibility = View.VISIBLE
    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus()

    }

    private fun showLoading() {

        binding.searchResult.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.VISIBLE
        binding.progressBarInEnd.visibility = View.GONE
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.VacancyContent -> showVacanciesList(state.vacancies, state.foundValue)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun clearInputEditText() {
        binding.searchEditText.setText("")
        viewModel.clearInputEditText()

        binding.searchResult.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE
        hideKeyBoard()
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener() {

        vacancySearchDebounce = debounce<String>(
            SEARCH_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            true
        ) { text ->
            viewModel.searchVacancy(text)
        }

        binding.searchEditText.setOnFocusChangeListener { _, hasFocus ->
            viewModel.setOnFocus(binding.searchEditText.text.toString(), hasFocus)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                vacancySearchDebounce(s.toString())
                viewModel.setOnFocus(s.toString(), binding.searchEditText.hasFocus())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchVacancy(binding.searchEditText.text.toString())
                true
            }
            false
        }

        binding.editTextCloseImage.setOnClickListener {
            clearInputEditText()
        }

        adapter.itemClickListener = { position, vacancy ->
            onVacancyClickDebounce(vacancy)
        }

        onVacancyClickDebounce = debounce<Vacancy>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            openVacancy(vacancy)
        }
    }

    private fun openVacancy(vacancy: Vacancy) {

        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
            VacancyFragment.createArgs(Gson().toJson(vacancy))
        )
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}
