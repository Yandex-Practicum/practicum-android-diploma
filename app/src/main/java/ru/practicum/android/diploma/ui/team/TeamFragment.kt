package ru.practicum.android.diploma.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.ui.root.BindingFragment

class TeamFragment : BindingFragment<FragmentTeamBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTeamBinding {
        return FragmentTeamBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopbar()
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.team_body,
            android.R.layout.simple_list_item_1
        )
        binding.teamList.adapter = adapter
    }

    private fun initTopbar() {
        binding.topbar.apply {
            btnFirst.isVisible = false
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.team)
        }
    }
}
