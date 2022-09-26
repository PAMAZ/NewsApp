package it.paolomazza.newsapp.presentation.adapter.item

enum class NewsType : BaseItemType {
    NEWS, UNKNOWN;

    companion object {
        fun valueOf(value: Int): NewsType {
            for (aEnum in values()) {
                if (aEnum.ordinal == value) {
                    return aEnum
                }
            }
            return UNKNOWN
        }
    }
}