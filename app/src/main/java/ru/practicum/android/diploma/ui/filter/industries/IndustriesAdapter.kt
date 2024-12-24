package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto

class IndustriesAdapter(
    val listener: ChoiceIndustryFragment,
    private var industries: List<IndustriesFullDto> = ArrayList()
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    fun getIndustries(): List<IndustriesFullDto> = industries
    fun interface Listener {
        fun onClick(industry: IndustriesFullDto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_industry_list_item, parent, false)
        return IndustriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return industries.size
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        holder.bind(industries[position], listener)
        holder.itemView.setOnClickListener { listener.onClick(industries[position]) }
    }

    fun updateIndustries(newIndustries: List<IndustriesFullDto>) {
        industries = newIndustries
        notifyDataSetChanged()
    }
}

class IndustriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val industryNameTextView: TextView = itemView.findViewById(R.id.tvName)

    fun bind(model: IndustriesFullDto, listener: IndustriesAdapter.Listener) {
        industryNameTextView.text = model.name
        itemView.setOnClickListener {
            listener.onClick(model)
        }
    }
}
