package it.paolomazza.newsapp.presentation.adapter.item

interface BaseItemType {

    fun getOrdinalNumber(): Int {
        return (this as Enum<*>).ordinal
    }
}