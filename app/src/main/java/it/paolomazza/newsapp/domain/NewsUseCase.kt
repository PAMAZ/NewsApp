package it.paolomazza.newsapp.domain

import androidx.paging.PagingData
import it.paolomazza.newsapp.data.entity.NewsModel
import it.paolomazza.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
        private val newsRepository: NewsRepository
) {

    suspend fun getNewsApi(): Flow<PagingData<NewsModel>> = newsRepository.getNews()

}