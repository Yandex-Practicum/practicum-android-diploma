package ru.practicum.android.diploma.filter.industry.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class BranchAdapter(private val clickListener: (branch: Industry) -> Unit) :
    RecyclerView.Adapter<BranchViewHolder>() {
    val branches = ArrayList<Industry>()

    private var selectedPosition = -1

    var branchName: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.industry_item, parent, false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(branches[position])
        holder.rb.isChecked = position == selectedPosition
        holder.name.text = branches[position].name
        holder.itemView.setOnClickListener { clickListener.invoke(branches[position]) }
        holder.rb.setOnClickListener {
            holder.itemView.rootView.findViewById<Button>(R.id.btnSave).isVisible = true
            selectedPosition = holder.adapterPosition
            branchName = branches[holder.adapterPosition].name
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return branches.size
    }
}
