package it.paolomazza.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.paolomazza.newsapp.data.entity.NewsListModel
import it.paolomazza.newsapp.data.entity.NewsModel
import it.paolomazza.newsapp.presentation.State

class NewsPagingSource(
        private val newsWork: suspend (Int) -> State<NewsListModel>
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