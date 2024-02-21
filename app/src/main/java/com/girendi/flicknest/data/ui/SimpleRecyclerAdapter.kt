package com.girendi.flicknest.data.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SimpleRecyclerAdapter<T: Any>(
    private val context: Context,
    private val layoutResId: Int,
    private val bindViewHolder: (View, T) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<SimpleRecyclerAdapter<T>.SimpleViewHolder> () {

    private var listItem: List<T> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(layoutResId, parent, false)

        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: SimpleRecyclerAdapter<T>.SimpleViewHolder,
        position: Int
    ) {
        val item = listItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListItem(list: List<T>) {
        this.listItem = list
        notifyDataSetChanged()
    }

    inner class SimpleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            bindViewHolder(itemView, item)
        }
    }

}