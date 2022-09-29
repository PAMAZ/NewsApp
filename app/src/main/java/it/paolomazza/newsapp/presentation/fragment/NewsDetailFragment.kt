package it.paolomazza.newsapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import it.paolomazza.newsapp.R
import it.paolomazza.newsapp.data.entity.NewsDetailModel
import it.paolomazza.newsapp.databinding.FragmentDetailNewsBinding
import it.paolomazza.newsapp.domain.NewsDetailViewModel
import it.paolomazza.newsapp.presentation.State
import it.paolomazza.newsapp.utils.UiUtils.setVisibilityByCondition

@AndroidEntryPoint
class NewsDetailFragment: BaseFragment<FragmentDetailNewsBinding>(R.layout.fragment_detail_news) {

    companion object{
        const val NEWS_ID = "NEWS_ID"
    }

    private val newsViewModel by viewModels<NewsDetailViewModel>()

    private val selectedNewsId by lazy { arguments?.get(NEWS_ID) as Int }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.newsDetailLiveData.observe(viewLifecycleOwner){
            binding.progressBarDetailNews.setVisibilityByCondition(it is State.LoadingState)
            binding.errorListView.root.setVisibilityByCondition(it is State.ErrorState)
            binding.successLayout.setVisibilityByCondition(it is State.Success)
            if(it is State.Success){
                populateNews(it.data)
            }
        }

        retrieveNewsId()
        showToolbar(true)
    }



    private fun populateNews(newsDetailModel: NewsDetailModel){
       binding.newsDetailModel =  newsDetailModel
    }

    private fun retrieveNewsId(){
        newsViewModel.getNewsDetail(selectedNewsId)
    }

}