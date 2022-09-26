package it.paolomazza.newsapp.data.repository

import androidx.paging.*
import it.paolomazza.newsapp.data.API
import it.paolomazza.newsapp.data.BaseRepository
import it.paolomazza.newsapp.data.entity.NewsListModel
import it.paolomazza.newsapp.data.entity.NewsModel
import it.paolomazza.newsapp.data.mapper.NewsListConverter.toNewsModel
import it.paolomazza.newsapp.presentation.State
import it.paolomazza.newsapp.utils.Constants
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
                    newsApi.getNews(nextOffset,nextOffset+DEFAULT_NEWS_LIMIT).toResult { dto ->
                        dto.toNewsModel()
                    }
                }
            }
    ).flow

    private class NewsPagingSource(
            val newsWork: suspend (Int) -> State<NewsListModel>
    ) : PagingSource<Int, NewsModel>() {


        override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            } ?: 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
            val offsetData = params.key ?:0
            return try {
                when (val result = newsWork.invoke(offsetData)) {
                    is State.Success -> {
                        LoadResult.Page(result.data.news, null, offsetData.plus(1))
                    }
                    is State.ErrorState -> LoadResult.Error(result.exception)
                    else -> LoadResult.Error(Exception("Unexpected result"))
                }
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

    }

}