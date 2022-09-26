package it.paolomazza.newsapp.presentation.adapter.view_holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import it.paolomazza.newsapp.presentation.adapter.item.BaseItem

abstract class BaseViewHolder(view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {

    abstract fun bind(item: BaseItem, position: Int)

}