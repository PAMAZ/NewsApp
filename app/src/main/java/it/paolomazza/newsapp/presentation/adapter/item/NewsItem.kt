package it.paolomazza.newsapp.presentation.adapter.item

import it.paolomazza.newsapp.data.entity.NewsModel

class NewsItem(val newsModel: NewsModel): BaseItem {

    override val type: BaseItemType
        get() = NewsType.NEWS

    override fun isSameItem(otherItem: BaseItem): Boolean {
        return otherItem is NewsItem
    }

    override fun areContentsTheSame(otherItem: BaseItem): Boolean {
        otherItem as NewsItem
        return otherItem.newsModel == this.newsModel
    }
}