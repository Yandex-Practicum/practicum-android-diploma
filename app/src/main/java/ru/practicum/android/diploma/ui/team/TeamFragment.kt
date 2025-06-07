package ru.practicum.android.diploma.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.ui.root.BindingFragment

class TeamFragment: BindingFragment<FragmentTeamBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTeamBinding {
        return FragmentTeamBinding.inflate(inflater, container, false)
    }
}
