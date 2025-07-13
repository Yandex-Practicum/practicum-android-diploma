package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.VacancyDetailsFragmentBinding
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private var _binding: VacancyDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<VacancyDetailsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancyDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnShare.setOnClickListener {
            val linkVacancy = "Здесь будет ссылка на вакнсию"
            viewModel.shareVacancy(linkVacancy)
        }

        binding.btnFavorite.setOnClickListener {  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
