package it.paolomazza.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import it.paolomazza.newsapp.databinding.ItemNewsBinding
import it.paolomazza.newsapp.presentation.adapter.item.BaseItem
import it.paolomazza.newsapp.presentation.adapter.item.NewsType
import it.paolomazza.newsapp.presentation.adapter.item.NewsViewHolder
import it.paolomazza.newsapp.presentation.adapter.view_holder.BaseViewHolder

class NewsAdapter(private val clickListenerCallBack: (Int) -> Unit) : PagingDataAdapter<BaseItem, BaseViewHolder>(AdapterDiffCallBack()) {

    private class AdapterDiffCallBack : DiffUtil.ItemCallback<BaseItem>() {
        override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean =
                oldItem.isSameItem(newItem)

        override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean =
                oldItem.areContentsTheSame(newItem)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (NewsType.valueOf(viewType)) {
            NewsType.NEWS -> NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),
                                                                    parent,
                                                                    false),clickListenerCallBack)
            NewsType.UNKNOWN -> TODO()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun submitBaseItemData(
            lifecycle: Lifecycle,
            pagingData: PagingData<out BaseItem>
    ) {
        submitData(lifecycle,pagingData as PagingData<BaseItem>)
    }
}