package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val viewModel by viewModel<VacancyFragmentViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancy = Vacancy("id", "mock", "mock", "mock", "mock", "mock")
        binding.ivSharing.setOnClickListener {
            viewModel.observeShareState().observe(viewLifecycleOwner) { sData ->
                shareVacancy(sData)
            }
        }

        vacancy?.let { viewModel.isVacancyInFavorites(it.id) }

        binding.ivFavorites.setOnClickListener {
            viewModel.onFavoriteClicked(vacancy)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareVacancy(data: ShareData) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, data.url)
            setType("text/plain")
        }, null)
        startActivity(share)
    }
}
