package ru.practicum.android.diploma.ui.searchfilters.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemWorkplaceSelectableBinding
import ru.practicum.android.diploma.domain.models.filters.Region

class RegionsAdapter(private val clickListener: OnClickListener) : ListAdapter<Region, RegionsItemViewHolder>(
    object : DiffUtil.ItemCallback<Region>() {
        override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionsItemViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return RegionsItemViewHolder(ItemWorkplaceSelectableBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: RegionsItemViewHolder, position: Int) {
        val region = getItem(position)
        holder.bind(region)
        holder.itemView.setOnClickListener {
            clickListener.onClick(region)
        }
    }

    interface OnClickListener {
        fun onClick(region: Region)
    }
}
