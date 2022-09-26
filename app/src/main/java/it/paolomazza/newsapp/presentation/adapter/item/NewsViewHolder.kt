package it.paolomazza.newsapp.presentation.adapter.item

import android.animation.ObjectAnimator
import android.view.View
import it.paolomazza.newsapp.databinding.ItemNewsBinding
import it.paolomazza.newsapp.presentation.adapter.view_holder.BaseViewHolder

class NewsViewHolder(private val viewBinding: ItemNewsBinding) : BaseViewHolder(viewBinding) {

	private var arrowOpen = false

	override fun bind(item: BaseItem, position: Int) {
		item as NewsItem
		viewBinding.newsModel = item.newsModel
		viewBinding.newsArrow.setOnClickListener {
			viewBinding.newsSubtitle.visibility = if(arrowOpen){
				View.GONE
			}else{
				View.VISIBLE
			}
			val angle = if(arrowOpen){
				180f
			}else{
				0f
			}
			ObjectAnimator.ofFloat(viewBinding.newsArrow, View.ROTATION, angle, angle+180f).setDuration(300).start();
			arrowOpen = !arrowOpen
		}
	}

}