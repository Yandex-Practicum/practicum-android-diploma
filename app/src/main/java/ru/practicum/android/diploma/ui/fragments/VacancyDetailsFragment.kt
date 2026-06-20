package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding

class VacancyDetailsFragment : Fragment() {
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupServerErrorState()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupServerErrorState() {
        binding.layoutServerError.ivPlaceholderPicture.setImageResource(R.drawable.placeholder_server_error_vacancy)
        binding.layoutServerError.tvPlaceholderText.text = getString(R.string.server_error)
        binding.layoutServerError.tvPlaceholderText.isVisible = true
    }

    private fun showServerError() {
        binding.layoutServerError.root.isVisible = true
        binding.vacancyContent.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
