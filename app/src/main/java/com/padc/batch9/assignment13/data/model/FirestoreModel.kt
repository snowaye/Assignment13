package com.padc.batch9.assignment13.data.model

import android.net.Uri
import androidx.lifecycle.LiveData
import com.padc.batch9.assignment13.data.vo.ArticleVO

interface FirestoreModel {
    fun getAllArticles(cleared: LiveData<Unit>): LiveData<List<ArticleVO>>

    fun getArticleById(id: String, cleared: LiveData<Unit>): LiveData<ArticleVO>

    fun updateClapCount(count: Int, article: ArticleVO)

    fun addComment(comment: String, pickedImage: Uri?, article: ArticleVO)
}