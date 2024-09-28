package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.VacancyFragmentBinding

class VacancyFragment : Fragment() {
    private lateinit var binding: VacancyFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VacancyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
