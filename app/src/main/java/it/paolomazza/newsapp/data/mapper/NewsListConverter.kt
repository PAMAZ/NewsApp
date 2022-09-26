package it.paolomazza.newsapp.data.mapper

import it.paolomazza.newsapp.data.entity.NewsListModel
import it.paolomazza.newsapp.data.entity.NewsModel
import it.paolomazza.newsapp.data.dto.GetNewsListDTO
import it.paolomazza.newsapp.utils.TimeUtils.timestampToDate

object NewsListConverter {

    fun GetNewsListDTO.toNewsModel(): NewsListModel {
        val newsList = this.message?.result?.map { newsDTO ->
            NewsModel(newsDTO.id, newsDTO.title, newsDTO.subtitle, newsDTO.timestamp?.timestampToDate())
        }?: listOf()
        return NewsListModel(0,newsList)
    }

}