package com.pemwa.topnews.view.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.databinding.NewsItemBinding

class NewsOverviewAdapter : ListAdapter<Article, NewsOverviewAdapter.NewsOverviewViewHolder>(DiffCallback) {

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsOverviewViewHolder {
        return NewsOverviewViewHolder.from(parent)
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     *
     * The ViewHolder passed may be recycled, therefore this sets any properties that
     * may have been set previously.
     */
    override fun onBindViewHolder(holder: NewsOverviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NewsOverviewViewHolder(private var binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article) {
            binding.article = article

            // Forcing the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : NewsOverviewViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
                return NewsOverviewViewHolder(binding)
            }
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}