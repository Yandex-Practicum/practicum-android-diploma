package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyDetailsFragmentBinding

class VacancyDetailsFragment : Fragment() {

    private var _binding: VacancyDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancyDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.toolbarTitle.text = getString(R.string.vacancy_details)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
