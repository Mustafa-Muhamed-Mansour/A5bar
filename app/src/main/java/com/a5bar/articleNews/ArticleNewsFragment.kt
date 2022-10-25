package com.a5bar.articleNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.a5bar.breakingNews.BreakingNewsViewModel
import com.a5bar.breakingNews.BreakingNewsViewModelFactory
import com.a5bar.databinding.FragmentArticleNewsBinding
import com.a5bar.model.NewsModel
import com.a5bar.network.local.NewsDB
import com.a5bar.repository.BreakingNewsRepository
import com.a5bar.ui.MainActivity
import com.google.android.material.snackbar.Snackbar

class ArticleNewsFragment : Fragment() {

    lateinit var viewModel: BreakingNewsViewModel
    private lateinit var binding: FragmentArticleNewsBinding
    private lateinit var breakingNewsRepository: BreakingNewsRepository
    private lateinit var breakingNewsViewModelFactory: BreakingNewsViewModelFactory

    private lateinit var article: NewsModel
    private val args: ArticleNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRepoAndFactory()
        initViewModel()
        clickedView()
    }

    private fun initView() {
        article = args.article
    }

    private fun initRepoAndFactory() {
        breakingNewsRepository =
            BreakingNewsRepository(NewsDB(requireActivity().applicationContext))
        breakingNewsViewModelFactory =
            BreakingNewsViewModelFactory(requireActivity().application, breakingNewsRepository)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, breakingNewsViewModelFactory)[BreakingNewsViewModel::class.java]
    }

    private fun clickedView() {
        binding.showArticleWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }

        binding.fabFavorite.setOnClickListener {
            viewModel.insertArticle(article)
            Snackbar.make(it, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}