package ru.practicum.android.diploma.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemLoadingBinding
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.ErrMessage
import ru.practicum.android.diploma.domain.models.RowType
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.SalaryPresenter

class SearchPagingAdapter(
    private val data: MutableList<RowType>,
    private val salaryPresenter: SalaryPresenter,
    private val clickListener: (Vacancy) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item = 0
    private val loading: Int = 1
    private var isLoadingAdded: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return if (viewType == item) SearchViewHolder(
            ItemVacancyBinding.inflate(layoutInspector, parent, false),
            salaryPresenter
        ) else LoadingVH(
            ItemLoadingBinding.inflate(layoutInspector, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == item) {
            val myHolder: SearchViewHolder = holder as SearchViewHolder
            myHolder.bind(data[position] as Vacancy)
            myHolder.itemView.setOnClickListener {
                clickListener.invoke(data[position] as Vacancy)
            }
        } else {
            val loadingVH: LoadingVH = holder as LoadingVH
            loadingVH.bind(data[position] as ErrMessage)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(listItems: List<Vacancy>) {
        for (item in listItems) {
            add(item)
        }
    }

    fun add(vacancy: Vacancy) {
        data.add(vacancy)
        notifyItemInserted(data.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            item
        } else {
            if (position == data.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    fun showLoading() {
        isLoadingAdded = true
        data.add(ErrMessage())
    }

    fun showError(errorMessage: String?) {
        data.add(ErrMessage(errorMessage))
        notifyItemInserted(data.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = data.size - 1
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}

class LoadingVH(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemRowBinding: ItemLoadingBinding = binding

    fun bind(errMessage: ErrMessage) {
        if (errMessage.text.isNullOrEmpty()) {
            itemRowBinding.loadmoreErrorlayout.isVisible = false
            itemRowBinding.loadmoreProgress.isVisible = true
        } else {
            itemRowBinding.loadmoreErrorlayout.isVisible = true
            itemRowBinding.loadmoreProgress.isVisible = false
            itemRowBinding.loadmoreErrortxt.text = errMessage.text
        }


    }
}