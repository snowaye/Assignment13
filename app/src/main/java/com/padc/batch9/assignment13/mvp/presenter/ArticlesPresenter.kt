package com.padc.batch9.assignment13.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.padc.batch9.assignment13.data.model.FirebaseModel
import com.padc.batch9.assignment13.data.model.FirebaseModelImpl
import com.padc.batch9.assignment13.data.model.UserAuthenticationModel
import com.padc.batch9.assignment13.data.model.UserAuthenticationModelImpl
import com.padc.batch9.assignment13.data.vo.ArticleVO
import com.padc.batch9.assignment13.delegate.ArticleItemDelegate
import com.padc.batch9.assignment13.mvp.view.ArticlesView

class ArticlesPresenter: BaseGoogleSignInPresenter<ArticlesView>(), ArticleItemDelegate {


    override fun onArticleItemClicked(data: ArticleVO) {
        mView.navigateToDetail(data.id)
    }

    private val model : FirebaseModel = FirebaseModelImpl
    private val userModel : UserAuthenticationModel = UserAuthenticationModelImpl
    private val clearedLiveData = MutableLiveData<Unit>()

    fun onUIReady(owner: LifecycleOwner){
        model.getAllArticles(clearedLiveData).observe(owner, Observer {
            mView.showArticles(it)
        })
    }


    override fun onCleared() {
        clearedLiveData.value = Unit
        super.onCleared()
    }

    fun onStart() {
        userModel.currentUser?.let {
            mView.showLoginUser(it)
        } ?: mView.showLogoutUser()
    }

    fun onUserProfileClicked(context: Context) {
        if (userModel.isLoginUser()){
            userModel.logOut()
            onStart()

        } else {
            googleSignIn(context)
        }
    }
}