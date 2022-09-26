package it.paolomazza.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import it.paolomazza.newsapp.R
import it.paolomazza.newsapp.databinding.FragmentNewsBinding
import it.paolomazza.newsapp.domain.NewsViewModel
import it.paolomazza.newsapp.presentation.adapter.NewsAdapter
import it.paolomazza.newsapp.presentation.adapter.NewsLoadingAdapter

class NewsListFragment:Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding!!

    private val newsViewModel by activityViewModels<NewsViewModel>()
    private var newsListAdapter:NewsAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.newsLiveData.observe(viewLifecycleOwner){ news ->
            newsListAdapter?.submitBaseItemData(viewLifecycleOwner.lifecycle,news)
        }
        setupUi()
        collectUiState()
    }

    private fun setupUi(){
        newsListAdapter = NewsAdapter()
        with(binding){
            recyclerNewsList.adapter = newsListAdapter?.withLoadStateHeaderAndFooter(
                    header = NewsLoadingAdapter {},
                    footer = NewsLoadingAdapter {}
            )
            newsListAdapter?.addLoadStateListener { loadState -> renderUi(loadState) }
            listOf(emptyListView.retryButton, errorListView.retryButton).forEach { button ->
                button.setOnClickListener {
                    collectUiState()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && newsListAdapter?.itemCount == 0
        binding
        binding.recyclerNewsList.isVisible = loadState.source.refresh is LoadState.NotLoading && !isListEmpty
        binding.progressBarNews.isVisible = loadState.source.refresh is LoadState.Loading
        binding.emptyListView.root.isVisible = isListEmpty
        binding.errorListView.root.isVisible = newsListAdapter?.itemCount == 0 && loadState.source.refresh is LoadState.Error
    }



    private fun collectUiState() {
        newsViewModel.getNewsList()
    }

}