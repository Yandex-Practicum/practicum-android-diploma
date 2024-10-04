package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.search.ui.presenter.RecycleViewAdapter
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.hideKeyboard

class VacancySearchFragment : Fragment() {
    companion object {
        const val CLICK_DEBOUNCE_DELAY = 2000L
    }
    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private var vacancies = mutableListOf<VacancySearch>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit()

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // коммент костыль
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s)
                TODO("Реализация поиска")
                if (binding.searchLine.hasFocus() && s?.isEmpty() == true) {
                    TODO("Дефолт экран вьюмодель")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // коммент костыль
            }
        }
        binding.searchLine.addTextChangedListener(searchTextWatcher)

        binding.searchLineCleaner.setOnClickListener {
            view.hideKeyboard()
            binding.searchLine.setText("")
            TODO("Дефолт экран вьюмодель")
        }

    }

    private fun clearButtonVisibility(s: CharSequence?) {
        val visibility = !s.isNullOrEmpty()
        binding.searchLineCleaner.isVisible = visibility
        binding.icSearch.isVisible = !visibility
    }

    private fun recyclerViewInit() {
        val onVacancyClickDebounce: ((VacancySearch) -> Unit) =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
               TODO("Реализовать клик в вьюмодел")
            }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = RecycleViewAdapter(vacancies, onVacancyClickDebounce)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

