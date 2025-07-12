package ru.practicum.android.diploma.ui.favouritevacancies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FavouriteVacanciesFragmentBinding
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.Debouncer

class FavouriteVacanciesFragment : Fragment() {

    private var _binding: FavouriteVacanciesFragmentBinding? = null
    private val binding get() = _binding!!

    private val vacancies = mutableListOf<VacancyUiModel>()
    private val adapter = VacancyItemAdapter(vacancies)

    private val debounce by lazy {
        Debouncer(viewLifecycleOwner.lifecycleScope, CLICK_DEBOUNCE_DELAY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FavouriteVacanciesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.toolbarTitle.text = getString(R.string.favorite)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 2000L
    }
}
