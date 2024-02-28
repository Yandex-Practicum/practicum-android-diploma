package ru.practicum.android.diploma.ui.similarvacancies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import ru.practicum.android.diploma.R

class SimilarVacanciesFragment : Fragment() {

    companion object {
        private const val VACANCYID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle =
            bundleOf(
                VACANCYID to vacancyId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_similar_vacancies, container, false)
    }
}
