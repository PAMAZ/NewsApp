package it.paolomazza.newsapp.presentation.adapter.item

interface BaseItem {
    val type: BaseItemType
    fun isSameItem(otherItem: BaseItem): Boolean
    fun areContentsTheSame(otherItem: BaseItem): Boolean
}