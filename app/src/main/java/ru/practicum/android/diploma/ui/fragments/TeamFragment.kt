package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.ui.adapter.TeamAdapter

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!
    private var adapter: TeamAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamAdapter()
        _binding?.rvTeam?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvTeam?.adapter = adapter

        // Подключаем мок-данные
        adapter?.submitList(adapter!!.getMockData())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
