package it.paolomazza.newsapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import it.paolomazza.newsapp.R
import it.paolomazza.newsapp.databinding.FragmentNewsBinding
import it.paolomazza.newsapp.domain.NewsViewModel
import it.paolomazza.newsapp.presentation.adapter.NewsAdapter
import it.paolomazza.newsapp.presentation.adapter.NewsLoadingAdapter

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding?
        get() = _binding

    private val newsViewModel by viewModels<NewsViewModel>()

    private var newsListAdapter = NewsAdapter {
        openNewsDetail(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.newsLiveData.observe(this) { news ->
            newsListAdapter.submitBaseItemData(this.lifecycle, news)
        }
        collectUiState()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if(_binding == null){
            _binding = FragmentNewsBinding.inflate(inflater, container, false)
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding?.apply {
            if(recyclerNewsList.adapter == null) {
                recyclerNewsList.adapter = newsListAdapter.withLoadStateHeaderAndFooter(
                        header = NewsLoadingAdapter {},
                        footer = NewsLoadingAdapter {}
                )

                newsListAdapter.addLoadStateListener { loadState ->
                    renderUi(loadState)
                }
            }

            listOf(emptyListView.retryButton, errorListView.retryButton).forEach { button ->
                button.setOnClickListener {
                    collectUiState()
                }
            }
        }

        (activity as AppCompatActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }

    }

    private fun openNewsDetail(newsId: Int) {
        val bundle = Bundle(bundleOf(NewsDetailFragment.NEWS_ID to newsId))
        findNavController().navigate(
                R.id.newsDetailFragment,
                bundle
        )
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        binding?.apply {
            val isListEmpty = loadState.refresh is LoadState.NotLoading && newsListAdapter.itemCount == 0
            recyclerNewsList.isVisible = loadState.source.refresh is LoadState.NotLoading && !isListEmpty
            progressBarNews.isVisible = loadState.source.refresh is LoadState.Loading
            emptyListView.root.isVisible = isListEmpty
            errorListView.root.isVisible = newsListAdapter.itemCount == 0 && loadState.source.refresh is LoadState.Error
        }

    }


    private fun collectUiState() {
        newsViewModel.getNewsList()
    }

}