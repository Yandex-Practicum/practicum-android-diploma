package ru.practicum.android.diploma.filter.profession.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.profession.domain.model.Industry

class FilterIndustryListAdapter(private val clickListener: IndustryClickListener) :
    RecyclerView.Adapter<IndustryListViewHolder>() {

    private var options = emptyList<Industry>()
    private var selectedPosition: Int? = null
    private var isSelectionActive = false

    fun setOptionsList(optionsList: List<Industry>) {
        options = optionsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return options.size
    }

    fun undoSelection() {
        selectedPosition?.let { position ->
            selectedPosition = null
            isSelectionActive = false
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryListViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return IndustryListViewHolder(ItemFilterIndustryBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: IndustryListViewHolder, position: Int) {
        val industry = options[position]
        holder.bind(industry)

        if (selectedPosition == position) {
            holder.circleIcon.setImageResource(ru.practicum.android.diploma.ui.R.drawable.filter_option_toggle_on)
        } else {
            holder.circleIcon.setImageResource(ru.practicum.android.diploma.ui.R.drawable.filter_option_toggle_off)
        }

        holder.itemView.setOnClickListener {
            if (selectedPosition == holder.adapterPosition) {
                undoSelection()
                clickListener.onIndustryClick(null)
            } else {
                selectedPosition?.let { prevPosition ->
                    notifyItemChanged(prevPosition)
                }
                selectedPosition = holder.adapterPosition
                notifyItemChanged(holder.adapterPosition)
                clickListener.onIndustryClick(industry)
            }
        }

    }

    interface IndustryClickListener {
        fun onIndustryClick(industry: Industry?)
    }
}
