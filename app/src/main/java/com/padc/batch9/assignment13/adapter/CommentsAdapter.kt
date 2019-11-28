package com.padc.batch9.assignment13.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.batch9.assignment13.R
import com.padc.batch9.assignment13.data.vo.CommentVO
import com.padc.batch9.assignment13.viewholder.CommentViewHolder

class CommentsAdapter: BaseAdapter<CommentViewHolder, CommentVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.viewholder_comment, parent, false
        )

        return CommentViewHolder(view)
    }
}