package com.padc.batch9.assignment13.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.batch9.assignment13.R
import com.padc.batch9.assignment13.data.vo.ArticleVO
import com.padc.batch9.assignment13.delegate.ArticleItemDelegate
import com.padc.batch9.assignment13.viewholder.ArticleViewHolder

class ArticlesAdapter(private val delegate: ArticleItemDelegate): BaseAdapter<ArticleViewHolder, ArticleVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item_article, parent, false)
        return ArticleViewHolder(itemView, delegate)
    }

}