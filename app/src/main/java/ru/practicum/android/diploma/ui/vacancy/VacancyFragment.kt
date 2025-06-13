package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.util.handleBackPress

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiToolbar()
        // системная кн назад
        handleBackPress()
    }

    private fun initUiToolbar(){
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForVacancyDetailScreen()
        toolbar.setToolbarTitle(getString(R.string.vacancy))
        toolbar.setupToolbarBackButton(this)
        // Поделиться
        toolbar.setOnToolbarShareClickListener {
            /* !!! Здесь будет Intent */
        }
        // Избранное
        toolbar.setOnToolbarFavoriteClickListener {
            /* !!! Реализация добавления в избранное */
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
