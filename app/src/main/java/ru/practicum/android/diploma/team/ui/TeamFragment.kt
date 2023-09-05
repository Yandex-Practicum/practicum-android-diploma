package ru.practicum.android.diploma.team.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.team.ui.model.TeamMember
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class TeamFragment : Fragment(R.layout.fragment_team), SwipeStack.SwipeStackListener {

    private val binding by viewBinding<FragmentTeamBinding>()
    @Inject lateinit var swipeAdapter: SwipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as RootActivity).component.inject(this)

        val list = getImgData()
        swipeAdapter.mData.addAll(list)
        binding.swipeStack.adapter = swipeAdapter
        binding.swipeStack.setListener(this)

    }

    private fun getImgData(): List<TeamMember> {
        val list = mutableListOf<TeamMember>()
        list.add(TeamMember(
            photo = R.drawable.lead,
            name = getString(R.string.lead),
            description = getString(R.string.alba_description)
        ))
        list.add(TeamMember(
            photo = R.drawable.albatross,
            name = getString(R.string.oldwise),
            description = getString(R.string.alba_description)
        ))
        list.add(TeamMember(
            photo = R.drawable.flash,
            name = getString(R.string.flash),
            description = getString(R.string.alba_description)
        ))
        list.add(TeamMember(
            photo = R.drawable.albatross,
            name = getString(R.string.meister),
            description = getString(R.string.alba_description)
        ))
        list.add(TeamMember(
            photo = R.drawable.albatross,
            name = getString(R.string.bitwise),
            description = getString(R.string.alba_description)
        ))
        Log.d("MyLog", "$thisName -> getImgData(): List<TeamMember>.size = ${list.size}")
        return list
    }

    override fun onViewSwipedToLeft(position: Int) {}
    override fun onViewSwipedToRight(position: Int) {}
    override fun onStackEmpty() {}
}