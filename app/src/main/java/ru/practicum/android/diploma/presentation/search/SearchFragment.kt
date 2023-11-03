package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.mok.Vacancy
import ru.practicum.android.diploma.domain.models.mok.getVacancies
import ru.practicum.android.diploma.presentation.detail.DetailFragment.Companion.addArgs
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var onItemClickDebounce: (Vacancy) -> Unit

    private val vacancies = mutableListOf<Vacancy>()
    private val adapter = SearchAdapter(vacancies) { vacanciy ->
        onItemClickDebounce(vacanciy)
    }


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
        binding.rvSearch.adapter = adapter
        vacancies.addAll(getVacancies())
        onItemClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            addArgs(vacancy)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }
        binding.startImage.isVisible = false
        binding.rvSearch.isVisible = true
        adapter.notifyDataSetChanged()

    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY_MILLIS = 200L
    }
}