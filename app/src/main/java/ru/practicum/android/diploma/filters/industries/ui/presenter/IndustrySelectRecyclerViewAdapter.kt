package ru.practicum.android.diploma.filters.industries.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryCardBinding
import ru.practicum.android.diploma.filters.industries.domain.models.Industry

class IndustrySelectRecyclerViewAdapter(
    private val clickListener: IndustryClickListener
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    var list = mutableListOf<Industry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val view = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(IndustryCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onClick(itemView)
            holder.selectItem()
        }
    }

    fun filterResults(request: String) {
        val filteredList = list.filter { industry ->
            industry.name
                .lowercase()
                .contains(request)
        }
        list.clear()
        list.addAll(filteredList)
        notifyDataSetChanged()
    }

    fun interface IndustryClickListener {
        fun onClick(industry: Industry)
    }
}
