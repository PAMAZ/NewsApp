package it.paolomazza.newsapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.paolomazza.newsapp.data.entity.NewsDetailModel
import it.paolomazza.newsapp.presentation.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
        private val newsUseCase: NewsUseCase): ViewModel() {

    private val _privateNewsDetailLiveData = MutableLiveData<State<NewsDetailModel>>()
    val newsDetailLiveData: LiveData<State<NewsDetailModel>> = _privateNewsDetailLiveData

    val defaultExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _privateNewsDetailLiveData.value = State.ErrorState(exception)
    }

    fun getNewsDetail(newsId: Int){
        viewModelScope.launch(defaultExceptionHandler) {

             _privateNewsDetailLiveData.value = State.LoadingState

            val result = newsUseCase.getNewsDetail(newsId)

            _privateNewsDetailLiveData.value =result
        }
    }

}