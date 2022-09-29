package it.paolomazza.newsapp.domain

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import it.paolomazza.newsapp.presentation.adapter.item.BaseItem
import it.paolomazza.newsapp.presentation.adapter.item.NewsItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
        private val newsUseCase: NewsUseCase):ViewModel() {

    private val _privateNewsLiveData = MutableLiveData<PagingData<out BaseItem>>()
    val newsLiveData: LiveData<PagingData<out BaseItem>> = _privateNewsLiveData.distinctUntilChanged()

    fun getNewsList() {
        viewModelScope.launch {
             newsUseCase.getNewsApi().map { pagingdata ->
                pagingdata.map {
                    NewsItem(it)
                }
            }.cachedIn(viewModelScope).collectLatest {
                _privateNewsLiveData.value = it
             }
        }
    }

}