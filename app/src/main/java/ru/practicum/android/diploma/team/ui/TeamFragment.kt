package ru.practicum.android.diploma.team.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.util.viewBinding

class TeamFragment : Fragment(R.layout.fragment_team) {

    private val binding by viewBinding<FragmentTeamBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.setOnClickListener {
            val intent = Intent(activity, TestActivity::class.java)
            startActivity(intent)

        }
    }
}