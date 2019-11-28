package com.padc.batch9.assignment13.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.padc.batch9.assignment13.data.vo.ArticleVO
import com.padc.batch9.assignment13.delegate.ArticleItemDelegate
import kotlinx.android.synthetic.main.view_holder_item_article.view.*

class ArticleViewHolder(itemView: View, delegate: ArticleItemDelegate): BaseViewHolder<ArticleVO>(itemView) {

    private val imageView = itemView.ivArticle
    private val tvTitle = itemView.tvTitle
    private val tvClaps = itemView.tvClaps
    private val tvComments = itemView.tvComments
    private val tvDate = itemView.tvDate

    init {
        itemView.setOnClickListener {
            data?.let { delegate.onArticleItemClicked(it) }
        }
    }

    override fun bindData(data: ArticleVO) {
        Glide.with(itemView.context)
            .load(data.img)
            .into(imageView)
        tvTitle.text = data.title

        if (data.claps > 0) {
            tvClaps.text = data.claps.toString()
        } else {
            tvClaps.text = ""
        }

        if (data.comments_count > 0) {
            tvComments.text = data.comments_count.toString()
        } else {
            tvComments.text = ""
        }

        tvDate.text = data.date

    }
}