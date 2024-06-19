package ru.practicum.android.diploma.presentation.vacancy

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.VACANCY_KEY

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    // private val vacancyViewModel by viewModel<VacancyViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancy: Vacancy? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(VACANCY_KEY, Vacancy::class.java)
        } else {
            arguments?.getParcelable(VACANCY_KEY)
        }
        if (vacancy != null) {
            // vacancyViewModel.setVacancy(vacancy)
        }
        prepareViews()
    }

    private fun prepareViews() {
        binding.favoriteButtonOff.setOnClickListener {
            // vacancyViewModel.insertFavoriteVacancy()
        }
        binding.favoriteButtonOn.setOnClickListener {
            // vacancyViewModel.deleteFavoriteVacancy()
        }
    }
}
