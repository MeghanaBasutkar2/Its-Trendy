package com.example.itstrending.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.itstrending.R
import com.example.itstrending.Utils.ImageUtils
import com.example.itstrending.Utils.NumberFormatter
import com.example.itstrending.data.TrendingResponse
import com.example.itstrending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.item_repo.view.*

class TrendingReposAdapter(
    val viewModel: TrendingViewModel, var list: ArrayList<TrendingResponse.ItemsObj>,
    private val context: Context)
    : RecyclerView.Adapter<TrendingReposAdapter.ReposViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : TrendingReposAdapter.ReposViewHolder {
        return ReposViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo,
            parent, false))
    }

    override fun onBindViewHolder(holder: TrendingReposAdapter.ReposViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TrendingResponse.ItemsObj) {
            itemView.apply {
                //using data binding to refer xml views directly
                txvTitle.text = item.name
                txvForks.text = NumberFormatter.formatDecimalNum(item.forksCount)
                txvDescription.text = item.description
                ImageUtils.loadImage(context, item.owner.avatar, R.drawable.ic_launcher_foreground,
                imvAvatar)
            }
        }
    }

    override fun getItemCount(): Int {
        if (list.size < 1) {
            Toast.makeText(context, context.resources.getString(R.string.msg_empty_string),
                Toast.LENGTH_LONG).show() }
        return list.size
    }
}