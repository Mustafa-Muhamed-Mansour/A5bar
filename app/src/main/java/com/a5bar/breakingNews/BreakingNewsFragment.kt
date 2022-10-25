package com.a5bar.breakingNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a5bar.R
import com.a5bar.interfaces.ClickArticle
import com.a5bar.adapter.NewsAdapter
import com.a5bar.common.Constant
import com.a5bar.common.Validation
import com.a5bar.databinding.FragmentBreakingNewsBinding
import com.a5bar.model.NewsModel
import com.a5bar.network.local.NewsDB
import com.a5bar.repository.BreakingNewsRepository
import com.a5bar.common.Resource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BreakingNewsFragment : Fragment(), ClickArticle
//    , NewsAdapter.ClickArticle, NewsAdapter.ClickSAveArticle
{


    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var viewModel: BreakingNewsViewModel
    private lateinit var breakingNewsRepository: BreakingNewsRepository
    private lateinit var breakingNewsViewModelFactory: BreakingNewsViewModelFactory
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var validation: Validation
//    private var isLoading = false
//    private var isLastPage = false
//    private var isScrolling = false

    private val bind get() = binding
//    private lateinit var bind: ErrorMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

//        viewModel = (activity as MainActivity).viewModel


//        newsAdapter.setOnItemClickListener {

//            val bundle = Bundle()
//            bundle.putString("url", it.url)
////            val bundle = Bundle()
////            bundle.putSerializable("article", it)
//            Navigation.findNavController(requireView()).navigate(
//                R.id.action_breakingNewsFragment_to_articleNewsFragment,
//                bundle
//            )

//            val article = Bundle().apply {
//                putSerializable("article", it)
//            }
//
//            findNavController().navigate(
//                R.id.action_breakingNewsFragment_to_articleNewsFragment,
//                article
//            )
//        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRepoAndFactory()
        initView()
        clickedViews()
        initAdapter()
        initViewModel()
        retrieveViewModel()

    }

    private fun initRepoAndFactory() {
        breakingNewsRepository = BreakingNewsRepository(NewsDB(requireContext()))
        breakingNewsViewModelFactory =
            BreakingNewsViewModelFactory(requireActivity().application, breakingNewsRepository)
    }

    private fun initView() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//
//                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
//                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
//                val isNotAtBeginning = firstVisibleItemPosition >= 0
//                val isTotalMoreThanVisible = totalItemCount >= 20
//                val shouldPaginate =
//                    isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
//                            isTotalMoreThanVisible && isScrolling
//                if (shouldPaginate) {
//                    MainScope().launch {
//                        delay(Constant.SEARCH_NEWS_TIME_DELAY)
//                        binding.editCountryCode.addTextChangedListener {
//                            it?.let {
//                                if (it.toString().isNotEmpty())
//                                    viewModel.getBreakingNews(it.toString())
//                            }
//                        }
//                    }
////                    viewModel.getBreakingNews(countryCode)
//                    isScrolling = false
//                } else {
//                    binding.rVBreakingNew.setPadding(0, 0, 0, 0)
//                }
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//        }
        validation = Validation()
    }

    private fun clickedViews() {
        bind.itemErrorMessage.btnRetry.setOnClickListener {
//            it.startAnimation(CustomAnimation.buttonAnime())
            bind.itemErrorMessage.root.visibility = View.INVISIBLE

//            viewModel.getBreakingNews(countryCode)
            MainScope().launch {
                delay(Constant.SEARCH_NEWS_TIME_DELAY)
                binding.editCountryCode.addTextChangedListener { editable ->
                    editable?.let {
                        if (editable.toString().isNotEmpty())
                            viewModel.getBreakingNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun initAdapter() {
//        bind.rVBreakingNew.apply {
//            newsAdapter = NewsAdapter(this@BreakingNewsFragment)
//            this.adapter = newsAdapter
//            this.layoutManager = LinearLayoutManager(requireContext())
//            this.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
//        }
//        newsAdapter = NewsAdapter(this@BreakingNewsFragment)
//        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rVBreakingNew.addOnScrollListener(this.scrollListener)


//        binding.rVBreakingNew.apply {
//            adapter = newsAdapter
//            layoutManager = LinearLayoutManager(activity)
////            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
//        }

        newsAdapter = NewsAdapter(this@BreakingNewsFragment)

    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, breakingNewsViewModelFactory)[BreakingNewsViewModel::class.java]
    }

    private fun retrieveViewModel() {
        viewModel.breakingNews.observe(viewLifecycleOwner)
        { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        bind.rVBreakingNew.apply {

                            if (validation.checkCountryCode(requireContext(), binding.editCountryCode.text.toString()).none()) {
                                binding.editCountryCode.requestFocus()
                                newsAdapter.differ.submitList(null)
                                this.adapter = newsAdapter
                                this.layoutManager = LinearLayoutManager(requireContext())
                                this.addItemDecoration(
                                    DividerItemDecoration(
                                        requireContext(),
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                                return@apply
                            }

                            else {
                                newsAdapter.differ.submitList(it.newsModel.toList())
                                this.adapter = newsAdapter
                                this.layoutManager = LinearLayoutManager(requireContext())
                                this.addItemDecoration(
                                    DividerItemDecoration(
                                        requireContext(),
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                            }
                        }
//                            .adapter
//                        = NewsAdapter(it.newsModel, this@BreakingNewsFragment)
//                        newsAdapter.differ.submitList(it.newsModel.toList())
//                        val totalPage = it.totalResults / 20 + 2
//                        viewModel.breakingNewsPage = totalPage
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
//                        Log.e("BreakingNewsFragment", "An error eccurred: $message")
//                        Toast.makeText(requireContext(), "An error eccurred: $message", Toast.LENGTH_SHORT).show()
                        showErrorMessage(message)
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
//        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
//        isLoading = true
    }

    private fun showErrorMessage(message: String) {
        bind.itemErrorMessage.root.visibility = View.VISIBLE
        bind.itemErrorMessage.txtErrorMessage.text = message
//        isError = true
    }

    override fun getClickArticle(newsModel: NewsModel) {

        val article = Bundle().apply {
            this.putSerializable("article", newsModel)
        }

        findNavController().navigate(
            R.id.action_breakingNewsFragment_to_articleNewsFragment,
            article
        )
    }

}