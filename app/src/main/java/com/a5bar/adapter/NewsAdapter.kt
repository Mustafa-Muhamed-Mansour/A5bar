package com.a5bar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.a5bar.R
import com.a5bar.databinding.ItemArticleBinding
import com.a5bar.interfaces.ClickArticle
import com.a5bar.model.NewsModel
import com.bumptech.glide.Glide

class NewsAdapter(private var clickArticle: ClickArticle) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

//    (var newsModel: List<NewsModel>, var click: ClickArticle)

    private val differCallBack = object : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    class ArticleViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide
            .with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.loading)
            .error(R.drawable.loading)
            .fitCenter()
            .into(holder.bind.imgItemArticle)
        holder.bind.txtSourceItemArticle.text = article.source!!.name
        holder.bind.txtTitleItemArticle.text = article.title
        holder.bind.txtDescriptionItemArticle.text = article.description
        holder.bind.txtPublishedAtItemArticle.text = article.publishedAt

//            onItemClickListener?.let { it(article) }


        holder.itemView.setOnClickListener {

            clickArticle.getClickArticle(article)

        }
    }

//    private var onItemClickListener: ((NewsModel) -> Unit)?= null

//    fun setOnItemClickListener(listener: (NewsModel) -> Unit)
//    {
//        onItemClickListener = listener
//    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}