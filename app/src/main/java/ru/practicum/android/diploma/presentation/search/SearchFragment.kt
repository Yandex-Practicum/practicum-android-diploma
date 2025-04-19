package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: error("Binding is not initialized")

    // TO DO private val adapter: VacancyAdapter = VacancyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            // TO DO recyclerView.adapter = adapter

            toFiltersButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_main_to_navigation_filters)
            }

            clearFieldButton.setOnClickListener {
                // TO DO clear text
            }
        }
    }

    // TO DO использовать в Адаптере
    @Suppress("unused")
    private fun navigateToVacancyScreen(vacancy: VacancyShort) {
        val args = bundleOf("vacancyId" to vacancy.vacancyId)
        findNavController().navigate(R.id.action_navigation_main_to_navigation_vacancy, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
