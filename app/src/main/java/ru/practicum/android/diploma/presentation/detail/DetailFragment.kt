package ru.practicum.android.diploma.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.VacanciesBinding
import ru.practicum.android.diploma.domain.models.mok.Vacancy

class DetailFragment : Fragment() {
    private var _binding: VacanciesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun addArgs(vacancy: Vacancy){

        }
    }
}
