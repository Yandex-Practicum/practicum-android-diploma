package ru.practicum.android.diploma.fav_vacancies.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.fav_vacancies.view_models.FavVacanciesViewModel

class FavVacanciesFragment : Fragment() {

    companion object {
        fun newInstance() = FavVacanciesFragment()
    }

    private val viewModel: FavVacanciesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fav_vacancies, container, false)
    }
}
