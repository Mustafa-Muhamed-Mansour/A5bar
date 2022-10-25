package com.a5bar.searchNews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5bar.R
import com.a5bar.interfaces.ClickArticle
import com.a5bar.adapter.NewsAdapter
import com.a5bar.common.Constant
import com.a5bar.common.Validation
import com.a5bar.databinding.FragmentSearchNewsBinding
import com.a5bar.model.NewsModel
import com.a5bar.repository.SearchNewsRepository
import com.a5bar.common.Resource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(), ClickArticle {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var searchNewsRepository: SearchNewsRepository
    private lateinit var searchNewViewModelFactory: SearchNewViewModelFactory
    private lateinit var viewModel: SearchNewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var validation: Validation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRepoAndFactory()
        initViews()
        initAdapter()
        initViewModel()
        retrieveViewModel()


    }

    private fun initRepoAndFactory() {
        searchNewsRepository = SearchNewsRepository()
        searchNewViewModelFactory = SearchNewViewModelFactory(searchNewsRepository)
    }

    private fun initViews() {
        validation = Validation()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter(this@SearchNewsFragment)
    }


    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, searchNewViewModelFactory)[SearchNewsViewModel::class.java]
    }

    private fun retrieveViewModel() {
        binding.editSearch.addTextChangedListener { editSearch ->
            hideProgressBar()
            MainScope().launch {
                delay(Constant.SEARCH_NEWS_TIME_DELAY)
                editSearch?.let {
                    if (validation.checkSearch(requireContext(), editSearch.toString()).none()) {
                        binding.rVSearchNew.apply {
                            this.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            this.addItemDecoration(
                                DividerItemDecoration(
                                    requireContext(),
                                    DividerItemDecoration.VERTICAL
                                )
                            )
                        }
                    } else {
                        viewModel.searchNews(editSearch.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner)
        { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (validation.checkSearch(
                                requireContext(),
                                binding.editSearch.text.toString()
                            ).none()
                        ) {
                            binding.rVSearchNew.apply {
                                newsAdapter.differ.submitList(null)
                                this.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                this.addItemDecoration(
                                    DividerItemDecoration(
                                        requireContext(),
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                            }
                        } else {
                            binding.rVSearchNew.apply {
                                newsAdapter.differ.submitList(it.newsModel.toList())
                                this.adapter = newsAdapter
                                this.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                this.addItemDecoration(
                                    DividerItemDecoration(
                                        requireContext(),
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("BreakingNewsFragment", "An error eccurred: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun getClickArticle(newsModel: NewsModel) {
        val bundle = Bundle().apply {
            this.putSerializable("article", newsModel)
        }

        findNavController().navigate(R.id.action_searchNewsFragment_to_articleNewsFragment, bundle)
    }

}