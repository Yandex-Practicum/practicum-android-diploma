package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.VacancyDetailsFragmentBinding

class VacancyDetailsFragment : Fragment() {

    private var binding: VacancyDetailsFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = VacancyDetailsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

}
