package it.paolomazza.newsapp.presentation.adapter.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import it.paolomazza.newsapp.R
import it.paolomazza.newsapp.databinding.ItemNewsLoadStateFooterBinding

class NewsLoadStateViewHolder(
    private val binding: ItemNewsLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        retry.invoke()
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorDescription.text = loadState.error.localizedMessage
        }
        binding.progressLoadMore.isVisible = loadState is LoadState.Loading
        binding.errorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): NewsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_load_state_footer, parent, false)
            val binding = ItemNewsLoadStateFooterBinding.bind(view)
            return NewsLoadStateViewHolder(binding, retry)
        }
    }
}