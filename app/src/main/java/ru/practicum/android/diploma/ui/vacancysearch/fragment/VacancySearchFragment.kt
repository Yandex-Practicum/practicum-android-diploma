package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Debouncer

class VacancySearchFragment : Fragment() {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModel<VacanciesSearchViewModel>()

    private val vacancies = ArrayList<VacancyUiModel>()
    private val adapter = VacancyItemAdapter(vacancies)

    private val debouncer by lazy {
        Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_DEBOUNCE_DELAY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.toolbarTitle.text = getString(R.string.search_vacancy)
        binding.header.iconFilter.visibility = View.VISIBLE

        binding.header.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }

        searchViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            Log.d("Vacancies", vacancies.toString())
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ...
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.icon.setImageResource(R.drawable.close_24px)
                    binding.searchMainPlaceholder.visibility = View.GONE
                    binding.icon.isClickable = true
                    binding.icon.setOnClickListener {
                        binding.inputEditText.setText("")
                        val inputMethodManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                    }
                    debouncer.submit {
                        activity?.runOnUiThread {
                            searchViewModel.searchVacancies(s.toString())
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.searchMainPlaceholder.visibility = View.VISIBLE
                    binding.icon.setImageResource(R.drawable.search_24px)
                    binding.icon.isClickable = false
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // ...
            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
