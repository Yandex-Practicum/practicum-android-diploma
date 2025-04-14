package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

class VacancyFragment : Fragment() {

    private lateinit var binding: FragmentVacancyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vacancy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentVacancyBinding.bind(view)

        binding.bToSearch.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.bShare.setOnClickListener {
            Toast.makeText(
                requireContext(), "SHARING....", Toast.LENGTH_SHORT
            ).show()
        }

        binding.bFavorite.setOnClickListener {
            Toast.makeText(
                requireContext(), "ADDING TO DB....", Toast.LENGTH_SHORT
            ).show()
        }
    }
}
