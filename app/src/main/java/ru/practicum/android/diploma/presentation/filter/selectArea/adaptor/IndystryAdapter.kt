package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryAreaItemBinding
import ru.practicum.android.diploma.domain.models.filter.Industry

class IndystryAdapter(
    private var items: ArrayList<Industry>,
    private val clickListener: (Industry, Int) -> Unit
) : RecyclerView.Adapter<IndystryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndystryViewHolder =
        IndystryViewHolder(
            IndustryAreaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IndystryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(items[position], position)
        }
    }

    override fun getItemCount(): Int = items.size
}

