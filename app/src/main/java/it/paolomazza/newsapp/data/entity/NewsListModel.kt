package it.paolomazza.newsapp.data.entity

import it.paolomazza.newsapp.data.BaseModel

data class NewsListModel(val index:Int, val news: List<NewsModel>):BaseModel()