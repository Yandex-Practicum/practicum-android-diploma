package ru.practicum.android.diploma.ui.fav_vacancies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.fav_vacancies.view_models.FavVacanciesViewModel

class FavVacanciesFragment : Fragment() {

    companion object {
        fun newInstance() = FavVacanciesFragment()
    }

    private val viewModel: FavVacanciesViewModel by viewModel<FavVacanciesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fav_vacancies, container, false)
    }
}
