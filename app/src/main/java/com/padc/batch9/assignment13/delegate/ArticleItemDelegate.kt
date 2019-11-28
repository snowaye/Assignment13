package com.padc.batch9.assignment13.delegate

import com.padc.batch9.assignment13.data.vo.ArticleVO


interface ArticleItemDelegate {

    fun onArticleItemClicked(data: ArticleVO)
}