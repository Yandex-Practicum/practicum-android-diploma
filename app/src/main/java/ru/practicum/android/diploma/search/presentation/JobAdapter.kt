package ru.practicum.android.diploma.search.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.RecyclerVacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.Job


class JobAdapter(val onJobClickedCB: (Job) -> Unit) : RecyclerView.Adapter<JobViewHolder>() {

    var jobsList = mutableListOf<Job>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return JobViewHolder(
            RecyclerVacancyItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = jobsList.size

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobsList[position])
        holder.itemView.setOnClickListener {
            onJobClickedCB(jobsList[position])
        }
    }
}