package ru.practicum.android.diploma.ui.searchfilters.industryfilter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.filters.Industry

class IndustryItemAdapter(private val clickListener: OnClickListener) : ListAdapter<Industry, IndustryItemViewHolder>(
    object : DiffUtil.ItemCallback<Industry>() {
        override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var selectedId: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IndustryItemViewHolder(ItemIndustryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: IndustryItemViewHolder, position: Int) {
        val industry = getItem(position)
        val isSelected = industry.id == selectedId

        holder.bind(industry, isSelected) {
            clickListener.onClick(industry)
        }
    }

    fun setSelectedId(id: String) {
        selectedId = id
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(industry: Industry)
    }
}
