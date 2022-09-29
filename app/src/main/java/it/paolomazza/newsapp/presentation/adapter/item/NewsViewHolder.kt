package it.paolomazza.newsapp.presentation.adapter.item

import android.animation.ObjectAnimator
import android.view.View
import it.paolomazza.newsapp.databinding.ItemNewsBinding
import it.paolomazza.newsapp.presentation.adapter.view_holder.BaseViewHolder
import it.paolomazza.newsapp.utils.UiUtils.setVisibilityByCondition

class NewsViewHolder(private val viewBinding: ItemNewsBinding, private val clickListenerCallBack: (Int) -> Unit) :
    BaseViewHolder(viewBinding) {

    private var arrowOpen = false

    override fun bind(item: BaseItem, position: Int) {
        item as NewsItem
        viewBinding.apply {

            newsModel = item.newsModel

            with(newsDetailLink) {
                paint?.isUnderlineText = true
                setOnClickListener {
                    clickListenerCallBack.invoke(item.newsModel.id)
                }
            }
        }

    }

}