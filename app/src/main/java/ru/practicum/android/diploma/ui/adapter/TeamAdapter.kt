package ru.practicum.android.diploma.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.data.TeamItem

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    private var items: List<TeamItem> = emptyList()

    fun submitList(list: List<TeamItem>) {
        items = list
        notifyDataSetChanged()
    }

    fun getMockData(): List<TeamItem> = listOf(
        TeamItem("Бодалин Роман", "Разработчик"),
        TeamItem("Давыдова Ксения", "Разработчик"),
        TeamItem("Маканаев Хазрет", "Разработчик"),
        TeamItem("Максимова Виктория", "Разработчик"),
        TeamItem("Ставицкий Виктор", "Разработчик")
    )

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val memberName: TextView = itemView.findViewById(R.id.memberName)
        private val memberRole: TextView = itemView.findViewById(R.id.memberRole)

        fun bind(item: TeamItem) {
            memberName.text = item.teamMemberName
            memberRole.text = item.teamMemberRole ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
