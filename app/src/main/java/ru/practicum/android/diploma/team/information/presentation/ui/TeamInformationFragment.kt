package ru.practicum.android.diploma.team.information.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme

class TeamInformationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                VacancySearchAppTheme {
                    TeamInformationScreen()
                }
            }
        }
    }
}
