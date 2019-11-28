package com.padc.batch9.assignment13.mvp.view

import com.google.firebase.auth.FirebaseUser
import com.padc.batch9.assignment13.data.vo.ArticleVO

interface ArticlesView: BaseGoogleSignInView {

    fun navigateToDetail(id: String)
    fun showArticles(data: List<ArticleVO>)
    fun showLoginUser(user: FirebaseUser)
    fun showLogoutUser()
}