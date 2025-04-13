package ru.practicum.android.diploma.vacancy_details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.vacancy_details.view_models.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = VacancyDetailsFragment()
    }

    private val viewModel: VacancyDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_vacancy_details, container, false)
    }
}
