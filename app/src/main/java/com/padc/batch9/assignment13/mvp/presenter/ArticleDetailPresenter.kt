package com.padc.batch9.assignment13.mvp.presenter

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.padc.batch9.assignment13.data.model.*
import com.padc.batch9.assignment13.data.vo.ArticleVO
import com.padc.batch9.assignment13.mvp.view.ArticleDetailView

class ArticleDetailPresenter: BaseGoogleSignInPresenter<ArticleDetailView>() {

//    private val model: FirebaseModel = FirebaseModelImpl
    private val firestoreModel: FirebaseModel = FirestoreModelImpl
    private val userModel: UserAuthenticationModel = UserAuthenticationModelImpl
    private val clearedLiveData = MutableLiveData<Unit>()

    private lateinit var article: ArticleVO
    private var pickedImage: Uri? = null

    fun onUIReady(owner: LifecycleOwner, id: String) {
//        model.getArticleById(id, clearedLiveData).observe(owner, Observer {
//
//        })
        firestoreModel.getArticleById(id, clearedLiveData).observe(owner, Observer {
            mView.showArticle(it)
            article = it
        })
    }

    override fun onCleared() {
        clearedLiveData.value = Unit
        super.onCleared()
    }

    fun onClapped(count: Int) {
        //model.updateClapCount(1, article)
        firestoreModel.updateClapCount(1, article)
    }

    fun onCommentClicked(context: Context) {
        if (userModel.isLoginUser()){
            mView.showCommentInputView()
        } else {
            googleSignIn(context)
        }
    }

    fun onCommentSendClicked(comment: String) {
        if (comment.isNotEmpty() || pickedImage != null){
            firestoreModel.addComment(comment, pickedImage,  article)
        }
    }

    fun onImagePicked(uri: Uri) {
       pickedImage = uri
        mView.showPickedImage(uri)
    }
}