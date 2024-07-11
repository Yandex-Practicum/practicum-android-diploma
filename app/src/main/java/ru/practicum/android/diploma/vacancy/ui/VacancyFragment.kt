package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vacancyId = requireArguments().getInt(VACANCY_ID, 0)
        viewModel.loadVacancy(vacancyId)
        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is VacancyState.Content -> showContext(screenState)
                VacancyState.Error -> showError()
                VacancyState.Loading -> showLoading()
            }
        }
    }

    private fun showContext(screenState: VacancyState.Content) {
        val vacancyFull = screenState.vacancyFull
        val tmp = StringBuilder("")
        tmp.append("id: " + vacancyFull.id + "\n")
        tmp.append("name: " + vacancyFull.name + "\n")
        tmp.append("company: " + vacancyFull.company + "\n")
        tmp.append("salary: " + vacancyFull.salary + "\n")
        tmp.append("area: " + vacancyFull.area + "\n")
        tmp.append("icon: " + vacancyFull.icon + "\n")
        tmp.append("employment: " + vacancyFull.employment + "\n")
        tmp.append("experience: " + vacancyFull.experience + "\n")
        tmp.append("schedule: " + vacancyFull.schedule + "\n")
        tmp.append("description: " + vacancyFull.description + "\n")
        binding.tvTemp.text = tmp.toString()
    }

    private fun showError() {
        binding.tvTemp.text = "Error"
    }

    private fun showLoading() {
        binding.tvTemp.text = "Loading"
    }

    companion object {
        private const val VACANCY_ID = "VACANCY_ID"
        fun createArguments(vacancyId: Int): Bundle = bundleOf(VACANCY_ID to vacancyId)
    }

}
