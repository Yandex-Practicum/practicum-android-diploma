package ru.practicum.android.diploma.ui.vacancy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.ui.vacancy.viewmodel.VacancyViewModel

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyViewModel by viewModels()


    // Временная заглушка + fix detekt
    companion object {
        private const val DEFAULT_VACANCY_ID: Long = 123L
    }

    private val vacancyId: Long = DEFAULT_VACANCY_ID


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }

        backButton()
        shareButton()
        favoriteButton(vacancyId)
    }

    private fun backButton() =
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

    private fun favoriteButton(vacancyId: Long) =
        binding.imageViewFavorites.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

    private fun shareButton() {
        binding.imageViewSharing.setOnClickListener {
            viewModel.vacancy.value?.let { vacancy ->
                val shareText = "${vacancy.name ?: "Вакансия"}\n\n${vacancy.alternateUrl ?: ""}"
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                startActivity(Intent.createChooser(shareIntent, ""))
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val resId = if (isFavorite) {
            R.drawable.ic_like_on_24
        } else {
            R.drawable.ic_like_off_24
        }
        binding.imageViewFavorites.setImageResource(resId)
    }
}
