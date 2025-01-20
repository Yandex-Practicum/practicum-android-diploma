package ru.practicum.android.diploma.ui.team.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentTeamBinding

class TeamFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding

//    private val viewModel: TeamViewModel by viewModel()

    private lateinit var templateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        templateTextView = binding.templateText
    }
}
