package com.padc.batch9.assignment13.mvp.view

import android.net.Uri
import com.padc.batch9.assignment13.data.vo.ArticleVO

interface ArticleDetailView : BaseGoogleSignInView{

    fun showArticle(data: ArticleVO)
    fun showCommentInputView()
    fun showPickedImage(uri: Uri)
}