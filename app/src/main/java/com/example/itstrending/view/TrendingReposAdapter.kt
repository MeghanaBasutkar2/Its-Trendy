package com.example.itstrending.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.itstrending.R
import com.example.itstrending.Utils.NumberFormatter
import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.item_repo.view.*
import kotlin.properties.Delegates

class TrendingReposAdapter(var viewModel: TrendingViewModel, var list: ArrayList<TrendingResponse.ItemsObj>,
                           private val context: Context) :
    RecyclerView.Adapter<TrendingReposAdapter.ReposViewHolder>() {
    var isListSet: Boolean = false

    /**
     * inflates the itemview
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        )
    }

    /**
     * fun keeps track of the currently selected position
     */
    private var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
        if (oldPos != -1 && newPos in list.indices) {
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(list[position], (position == selectedPosition))
    }

    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.layoutParent.setOnClickListener {
                selectedPosition = adapterPosition
                notifyItemChanged(adapterPosition)
                viewModel.setSelectedWithIndex(adapterPosition)
            }
        }

        fun bind(item: TrendingResponse.ItemsObj, selected: Boolean) {
            itemView.apply {
                //using data binding to refer xml views directly
                txvTitle.text = item.name
                txvForks.text = itemView.resources.getString(
                    R.string.forks,
                    NumberFormatter.formatDecimalNum(item.forksCount)
                )
                txvDescription.text = item.description

                when {
                    item.forksCount > 0 -> imvFork.visibility = View.VISIBLE
                }

                val radius = 8
                ImageUtils.loadImage(context, item.owner.avatar, R.drawable.ic_launcher_foreground,
                    imvAvatar, radius)
                layoutParent.isSelected = selected
            }
            setSelectionPrev()
        }
    }

    /**
     * when the config changes & something was selected before config change
     * */
    fun setSelectionPrev() {
        if (selectedPosition == -1 && viewModel.getSelectedIndex().value != null) {
            selectedPosition = viewModel.getSelectedIndex().value!!
        }
    }

    override fun getItemCount(): Int {
        if (list.size < 1 && isListSet) {
            Toast.makeText(
                context, context.resources.getString(R.string.msg_empty_string),
                Toast.LENGTH_LONG
            ).show()
        }
        return list.size
    }
}