package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.filter.Industry

class IndustryAdapter(
    private var items: ArrayList<Industry>,
    private val clickListener: (Industry, Int) -> Unit
) : RecyclerView.Adapter<IndustryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder =
        IndustryViewHolder(
            ItemIndustryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(items[position], position)
        }
    }

    override fun getItemCount(): Int = items.size
}

