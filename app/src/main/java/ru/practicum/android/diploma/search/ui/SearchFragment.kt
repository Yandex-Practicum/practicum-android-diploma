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
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
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

    /* val startForResult =
         registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
             if (result.resultCode == Activity.RESULT_OK) {

                 if (inputEditText.text.isNotEmpty()) {
                     refresh(inputEditText.text)
                     viewModel.getHistoryTracks()
                 } else {
                     viewModel.getHistoryTracks()
                 }
             }
         }*/

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
        // viewModel.getHistoryTracks()
        adapter = VacancyAdapter(ArrayList<Vacancy>())
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        listener()
        editTextRequestFocus()
    }

    private fun showVacanciesList(vacancy: List<Vacancy>) {

        binding.searchResult.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE

        hideKeyBoard()
        adapter.setVacancies(vacancy)
        adapter.notifyDataSetChanged()

        setSearchIconForEditText()
    }

    /* private fun showHistory(historyTrack: List<Track>) {

         if (inputEditText.text.isEmpty() && historyTrack.isNotEmpty() && inputEditText.hasFocus()) {
             progressBar.visibility = View.GONE
             recyclerView.visibility = View.VISIBLE
             history.visibility = View.VISIBLE
             removeButton.visibility = View.VISIBLE
             adapter.track.clear()
             adapter.track.addAll(historyTrack.toMutableList())
             adapter.notifyDataSetChanged()
         }
     }*/

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

    private fun changeIconInEditText(s: CharSequence?) {
        return if (s.isNullOrEmpty()) {
            setSearchIconForEditText()
        } else {
            setCloseIconForEditText()
        }
    }

    private fun setCloseIconForEditText() {
        binding.editTextImage.setImageDrawable(resources.getDrawable(R.drawable.close))
    }

    private fun setSearchIconForEditText() {
        binding.editTextImage.setImageDrawable(resources.getDrawable(R.drawable.search))
    }

    /* private fun removeHistory() {
         viewModel.removeTrackHistory()
         recyclerView.visibility = View.GONE
         history.visibility = View.GONE
         removeButton.visibility = View.GONE
         adapter.notifyDataSetChanged()
     }*/

    private fun refresh(s: CharSequence?) {
        viewModel.refreshSearchTrack(s?.toString() ?: "")
    }

    private fun editTextRequestFocus() {
        binding.searchEditText.postDelayed({ binding.searchEditText.requestFocus() }, 500)
    }

   /* @SuppressLint("RestrictedApi")
    private fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val focusedView = activity?.currentFocus
        if (focusedView != null) {
            imm?.hideSoftInputFromWindow(focusedView.windowToken, 0)
        }
//        ViewUtils.hideKeyboard(requireActivity().currentFocus ?: View(requireContext()))
    }*/

    private fun hideKeyBoard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
    }

    private fun showLoading() {

        binding.searchResult.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBarForLoad.visibility = View.VISIBLE
        binding.progressBarInEnd.visibility = View.GONE
    }

    fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.VacancyContent -> showVacanciesList(state.vacancies)
            is SearchState.HistroryContent -> {}
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun clearInputEditText() {
        binding.searchEditText.setText("")
        viewModel.clearInputEditText()
        editTextRequestFocus()

        binding.searchResult.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.progressBarForLoad.visibility = View.GONE
        binding.progressBarInEnd.visibility = View.GONE
        hideKeyBoard()
        adapter.notifyDataSetChanged()
    }

   /* private fun focusedViewCheck(s: CharSequence?) {
   //     val focusedView = activity?.currentFocus
        vacancySearchDebounce = debounce<String>(
            SEARCH_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            true
        ) { text ->
            //loadTracks(text) }

        //    if (focusedView != null) {
       //         viewModel.onSearchTextChanged(changedText = s?.toString() ?: "")
       //     } else {
                viewModel.searchVacancy(text)
            }
        }
  //  }*/

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener() {

        vacancySearchDebounce = debounce<String>(
            SEARCH_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            true
        ) { text ->
            //loadTracks(text) }

            //    if (focusedView != null) {
            //         viewModel.onSearchTextChanged(changedText = s?.toString() ?: "")
            //     } else {
            viewModel.searchVacancy(text)
        }

        binding.searchEditText.setOnFocusChangeListener { view, hasFocus ->
            viewModel.setOnFocus(binding.searchEditText.text.toString(), hasFocus)
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changeIconInEditText(s)
                vacancySearchDebounce(s.toString())
           //     focusedViewCheck(s)
            }

            override fun afterTextChanged(s: Editable?) {
       //         focusedViewCheck(s)
            }
        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchVacancy(binding.searchEditText.text.toString())
                true
            }
            false
        }

        /* refreshButton.setOnClickListener {
             refresh(inputEditText.text)
         }
         removeButton.setOnClickListener {
             removeHistory()
         }*/
        binding.editTextImage.setOnClickListener {
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

        /* Проверить переход на экран DetailsFragment!!!

        findNavController().navigate(
             R.id.action_searchFragment_to_vacancyFragment,
             VacancyFragment.createArgs(vacancy)
         )*/
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}
