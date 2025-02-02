package ru.practicum.android.diploma.ui.filter.country.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemListBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.diffutils.AreaDiffCallback

class AreaAdapter(
    private val onClick: (id: String, name: String) -> Unit,
) : ListAdapter<Area, AreaViewHolder>(AreaDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AreaViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AreaViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val area = getItem(position)
        holder.bind(area)
    }
}
