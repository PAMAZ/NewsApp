package it.paolomazza.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import it.paolomazza.newsapp.data.API
import it.paolomazza.newsapp.data.BaseRepository
import it.paolomazza.newsapp.data.NewsPagingSource
import it.paolomazza.newsapp.data.entity.NewsDetailModel
import it.paolomazza.newsapp.data.entity.NewsModel
import it.paolomazza.newsapp.data.mapper.NewsDetailConverter.toNewsDetailModel
import it.paolomazza.newsapp.data.mapper.NewsListConverter.toNewsModel
import it.paolomazza.newsapp.presentation.State
import it.paolomazza.newsapp.utils.Constants.DEFAULT_NEWS_LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
        private val newsApi: API
) : BaseRepository(Dispatchers.IO) {

    suspend fun getNews(): Flow<PagingData<NewsModel>> = Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                NewsPagingSource { offset ->
                    val nextOffset = offset * DEFAULT_NEWS_LIMIT
                    newsApi.getNews(nextOffset, nextOffset + DEFAULT_NEWS_LIMIT).toResult { dto ->
                        dto.toNewsModel()
                    }
                }
            }
    ).flow

    suspend fun getNewsDetail(id: Int): State<NewsDetailModel> = newsApi.getNewsDetail(id).toResult { dto ->
        dto.toNewsDetailModel()
    }

}