package com.example.myapplication.home

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListAdapter
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemArticle1Binding
import com.example.myapplication.databinding.ItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(val onItemClicked: (ArticleModel) -> Unit): androidx.recyclerview.widget.ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUTil) {

    inner class ViewHolder(private val binding: ItemArticle1Binding): RecyclerView.ViewHolder(binding.root){

        fun bind(articleModel: ArticleModel){

            binding.nameTextView.text = articleModel.name
            binding.dataTextView.text = articleModel.dataText
            binding.editTitleView.text = articleModel.title

            if(articleModel.imageUrl.isNotEmpty()) {
                Glide.with(binding.ImageDataView)
                    .load(articleModel.imageUrl)
                    .into(binding.ImageDataView)
            }

            if(articleModel.imageUrl.isNotEmpty())
            Glide.with(binding.ImageDataView)
                .load(articleModel.imageUrl)
                .into(binding.ImageDataView)

            binding.root.setOnClickListener {
                onItemClicked(articleModel)
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticle1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object  {
        val diffUTil = object : DiffUtil.ItemCallback<ArticleModel>(){
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}