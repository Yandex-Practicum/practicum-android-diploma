package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FavoriteFragmentBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.search.ui.presenter.RecycleViewAdapter
import ru.practicum.android.diploma.util.ClickListener
import ru.practicum.android.diploma.util.debounce

class FavoriteFragment : Fragment() {
    companion object{
        const val CLICK_DEBOUNCE_DELAY = 2000L
    }
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val vacancies = mutableListOf<VacancySearch>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit()

    }
    private fun recyclerViewInit() {
        val onVacancyClickDebounce: ((VacancySearch) -> Unit) =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
                TODO("Реализовать клик в вьюмодел")
            }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = RecycleViewAdapter(vacancies, onVacancyClickDebounce as ClickListener)
    // опять ругался поставил as ClickListener как заглушку. Починить при реализации
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
