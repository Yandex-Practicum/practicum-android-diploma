package ru.practicum.android.diploma.team.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.team.ui.model.TeamMember
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SwipeAdapter @Inject constructor(
    private val logger: Logger
) : BaseAdapter() {

    val mData = mutableListOf(TeamMember())

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): TeamMember {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        logger.log(thisName, "getView($position: Int, convertView: View?, parent: ViewGroup): View")
        val itemView: View
        if (position == 0) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.team_card, parent, false)
        } else {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.team_member_card, parent, false)
            val imageView = itemView.findViewById<ImageView>(R.id.photo)
            imageView.setImageResource(mData[position].photo)
            val name = itemView.findViewById<TextView>(R.id.name)
            name.text = mData[position].name
            val description = itemView.findViewById<TextView>(R.id.description)
            description.text = mData[position].description
            val container = itemView.findViewById<ConstraintLayout>(R.id.card_container)
            container.setBackgroundColor(mData[position].color)
        }
        return itemView
    }

}