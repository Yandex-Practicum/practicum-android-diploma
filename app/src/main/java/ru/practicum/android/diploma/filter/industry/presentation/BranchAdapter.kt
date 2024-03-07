package ru.practicum.android.diploma.filter.industry.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class BranchAdapter(
    private val clickListener: IndustryItemClickLister
) : RecyclerView.Adapter<BranchViewHolder>() {
    val branches = mutableListOf<Pair<Industry, Boolean>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.industry_item, parent, false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(
            industry = branches[position].first,
            isSelected = branches[position].second
        ) {
            clickListener.onClick(it, position)
        }
    }

    fun setSelected(index: Int) {
        if (branches.size > index) {
            branches[index] = branches[index].copy(second = true)
        }
    }

    override fun getItemCount() = branches.size
}

fun interface IndustryItemClickLister {
    fun onClick(branch: Industry, position: Int)
}
