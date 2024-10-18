package ru.practicum.android.diploma.filter.industry.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

class FilterIndustryListAdapter(private val clickListener: IndustryClickListener) :
    RecyclerView.Adapter<IndustryListViewHolder>() {

    private var options = emptyList<IndustryModel>()
    private var selectedPosition: Int? = null
    private var isSelectionActive = false

    fun selectItemById(id: String?) {
        options.forEachIndexed { index, industryModel ->
            if (industryModel.id == id) {
                if (selectedPosition != index) {
                    undoSelection()
                    selectedPosition = index
                    notifyItemChanged(index)
                    clickListener.onIndustryClick(industryModel)
                }
                return
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOptionsList(optionsList: List<IndustryModel>) {
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
        fun onIndustryClick(industryModel: IndustryModel?)
    }
}
