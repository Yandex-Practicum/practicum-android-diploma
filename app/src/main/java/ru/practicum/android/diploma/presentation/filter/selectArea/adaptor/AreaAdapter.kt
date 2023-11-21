package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemAreaBinding
import ru.practicum.android.diploma.domain.models.filter.Area

class AreaAdapter(
    private val items: List<Area>,
    private val clickListener: (Area) -> Unit
) : RecyclerView.Adapter<AreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder =
        AreaViewHolder(
            ItemAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}
