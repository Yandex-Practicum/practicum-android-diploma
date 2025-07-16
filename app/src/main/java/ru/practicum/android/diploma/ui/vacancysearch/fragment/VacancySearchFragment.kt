package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.presentation.mappers.toUiModel
import ru.practicum.android.diploma.presentation.models.vacancies.VacanciesState
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.TopSpacingItemDecoration
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.dpToPx

class VacancySearchFragment : Fragment(), VacancyItemAdapter.Listener {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModel<VacanciesSearchViewModel>()

    private var vacanciesList = ArrayList<VacancyUiModel>()
    private val adapter = VacancyItemAdapter(vacanciesList, this)

    private var debouncer: Debouncer? = null

    private var debounceForPlaceholder: Debouncer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        debouncer = Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_DEBOUNCE_DELAY)
        debounceForPlaceholder = Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_ERROR_DELAY)

        binding.header.toolbarTitle.text = getString(R.string.search_vacancy)
        binding.header.iconFilter.visibility = View.VISIBLE
        binding.recyclerViewSearch.addItemDecoration(
            TopSpacingItemDecoration(
                dpToPx(RECYCLER_MARGIN_TOP, requireContext())
            )
        )

        binding.header.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }

        searchViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesState.Initial -> {
                    showInitialState()
                }

                is VacanciesState.Loading -> {
                    showLoadingState()
                }

                is VacanciesState.Success -> {
                    showSuccessState()
                    binding.searchMessage.text =
                        getString(R.string.found) + " ${state.totalFound} " + getString(R.string.vacancies)

                    vacanciesList.clear()
                    vacanciesList.addAll(state.vacancies.map { it.toUiModel() })
                    adapter.notifyDataSetChanged()
                    Log.d("Vacancies", vacanciesList.toString())
                }

                VacanciesState.Empty -> {
                    showEmptyState()
                }

                VacanciesState.NoInternet -> {
                    showNoInternetState()
                }

                VacanciesState.ServerError -> {
                    showServerErrorState()
                }
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ...
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    showNonEmptyInput()
                    debouncer?.submit {
                        activity?.runOnUiThread {
                            binding.progressBar.visibility = View.VISIBLE
                            searchViewModel.searchVacancies(s.toString())
                        }
                    }
                } else {
                    showEmptyInput()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // ...
            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)

        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                debouncer?.cancel()
                binding.progressBar.visibility = View.VISIBLE
                searchViewModel.searchVacancies(binding.inputEditText.text.toString())
                true
            }
            false
        }

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debouncer?.cancel()
        debounceForPlaceholder?.cancel()
        debouncer = null
        debounceForPlaceholder = null
        _binding = null
        searchViewModel.resetState()
    }

    companion object {
        private const val SEARCH_ERROR_DELAY = 700L
        private const val RECYCLER_MARGIN_TOP = 38F
    }

    override fun onClick(id: String) {
        Log.d("VacanciesID", id)
        val action = VacancySearchFragmentDirections.actionVacancySearchFragmentToVacancyDetailsFragment(vacancyId = id)
        findNavController().navigate(action)
    }

    private fun showInitialState() {
        binding.errorText.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.searchMessage.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.visibility = View.VISIBLE
        binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
    }

    private fun showLoadingState() {
        binding.errorText.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.searchMessage.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.visibility = View.GONE
    }

    private fun showSuccessState() {
        binding.errorText.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.searchMessage.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.VISIBLE
        binding.searchMainPlaceholder.visibility = View.GONE
    }

    private fun showEmptyState() {
        binding.progressBar.visibility = View.GONE
        binding.searchMessage.visibility = View.VISIBLE
        binding.searchMessage.text = getString(R.string.no_vacancies)
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.visibility = View.VISIBLE
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = getString(R.string.nothing_found)
        binding.searchMainPlaceholder.setImageResource(R.drawable.nothing_found_placeholder)
    }

    private fun showNoInternetState() {
        binding.searchMessage.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.setImageResource(R.drawable.no_internet_placeholder)
        debounceForPlaceholder?.submit {
            activity?.runOnUiThread {
                binding.progressBar.visibility = View.GONE
                binding.searchMainPlaceholder.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = getString(R.string.no_connection)
            }
        }
    }

    private fun showServerErrorState() {
        binding.searchMessage.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.setImageResource(R.drawable.server_error_placeholder)
        debounceForPlaceholder?.submit {
            activity?.runOnUiThread {
                binding.progressBar.visibility = View.GONE
                binding.searchMainPlaceholder.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = getString(R.string.server_error)
            }
        }
    }

    private fun showNonEmptyInput() {
        binding.icon.setImageResource(R.drawable.close_24px)
        binding.searchMainPlaceholder.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.icon.isClickable = true
        binding.icon.setOnClickListener {
            debouncer?.cancel()
            searchViewModel.resetState()
            vacanciesList.clear()
            adapter.notifyDataSetChanged()
            binding.errorText.visibility = View.GONE
            binding.searchMessage.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewSearch.visibility = View.GONE
            binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
            binding.inputEditText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    private fun showEmptyInput() {
        debouncer?.cancel()
        binding.searchMainPlaceholder.visibility = View.VISIBLE
        binding.icon.setImageResource(R.drawable.search_24px)
        binding.icon.isClickable = false
        binding.progressBar.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.searchMessage.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
        binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
    }
}
