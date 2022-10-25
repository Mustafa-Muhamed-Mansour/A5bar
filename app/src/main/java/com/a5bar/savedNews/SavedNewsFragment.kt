package com.a5bar.savedNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a5bar.R
import com.a5bar.interfaces.ClickArticle
import com.a5bar.adapter.NewsAdapter
import com.a5bar.databinding.FragmentSavedNewsBinding
import com.a5bar.model.NewsModel
import com.a5bar.network.local.NewsDB
import com.a5bar.repository.BreakingNewsRepository
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment(), ClickArticle {


    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var breakingNewsRepository: BreakingNewsRepository
    private lateinit var savedNewsViewModelFactory: SavedNewsViewModelFactory
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRepoAndFactory()
        initAdapter()
        swipeToDelete()
        initViewModel()
        retrieveViewModel()
    }

    private fun initRepoAndFactory() {
        breakingNewsRepository = BreakingNewsRepository(NewsDB(requireContext()))
        savedNewsViewModelFactory = SavedNewsViewModelFactory(breakingNewsRepository)
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter(this@SavedNewsFragment)
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticles(article)
                Snackbar.make(requireView(), "Successfully deleted article", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo deleted") {
                            viewModel.saveArticles(article)
                        }
                        show()
                    }
            }
        }).attachToRecyclerView(binding.rVSavedNew)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, savedNewsViewModelFactory)[SavedNewsViewModel::class.java]
    }

    private fun retrieveViewModel() {
        viewModel.getSaveArticles().observe(viewLifecycleOwner)
        {
            newsAdapter.differ.submitList(it)
            binding.rVSavedNew.apply {
                this.adapter = newsAdapter
                this.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                this.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    override fun getClickArticle(newsModel: NewsModel) {
        val bundle = Bundle().apply {
            this.putSerializable("article", newsModel)
        }
        findNavController().navigate(R.id.action_savedNewsFragment_to_articleNewsFragment, bundle)
    }
}