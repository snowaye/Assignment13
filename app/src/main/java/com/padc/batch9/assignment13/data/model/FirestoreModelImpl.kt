package com.padc.batch9.assignment13.data.model


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.padc.batch9.assignment13.data.vo.ArticleVO
import com.padc.batch9.assignment13.data.vo.CommentVO
import com.padc.batch9.assignment13.data.vo.UserVO
import com.padc.batch9.assignment13.util.REF_KEY_CLAP_COUNT
import com.padc.batch9.assignment13.util.REF_KEY_COMMENTS
import com.padc.batch9.assignment13.util.REF_PATH_ARTICLES
import com.padc.batch9.assignment13.util.STORAGE_FOLDER_PATH
import kotlin.collections.ArrayList


object FirestoreModelImpl: FirebaseModel {

    const val TAG = "FirebaseModel"

    private val fireStoreRef = FirebaseFirestore.getInstance()

    override fun getAllArticles(cleared: LiveData<Unit>): LiveData<List<ArticleVO>> {
        val liveData = MutableLiveData<List<ArticleVO>>()

        val articlesRef = fireStoreRef.collection(REF_PATH_ARTICLES)

        // Read from the database
        val realTimeListener = object: EventListener<QuerySnapshot> {
            override fun onEvent(dataSnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {

                val articles = ArrayList<ArticleVO>()
                if (dataSnapshot!=null) {
                    for (dc in dataSnapshot) {
                        val article = dc.toObject(ArticleVO::class.java)
                        article?.let{
                            articles.add(article)
                        }
                    }
                }
                Log.d(FirebaseModelImpl.TAG, "Value is: $articles")
                liveData.value = articles
            }

        }

        // Start real-time data observing
        val dataObserver = articlesRef.addSnapshotListener(realTimeListener)

        // Stop real-time data observing when Presenter's onCleared() was called
        cleared.observeForever(object : Observer<Unit>{
            override fun onChanged(unit: Unit?) {
                unit?.let {
                    dataObserver.remove()
                    cleared.removeObserver(this)
                }
            }
        })

        return liveData
    }

    override fun getArticleById(id: String, cleared: LiveData<Unit>): LiveData<ArticleVO> {
        val liveData = MutableLiveData<ArticleVO>()

        val articleRef = fireStoreRef.collection(REF_PATH_ARTICLES).document(id)

        articleRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot!=null) {
                val article = documentSnapshot.toObject(ArticleVO::class.java)?.also {
                    liveData.value = it
                }
            }
        }
        return liveData
    }

    override fun updateClapCount(count: Int, article: ArticleVO) {
        val articleRef = fireStoreRef.collection(REF_PATH_ARTICLES).document(article.id)
        articleRef.update(REF_KEY_CLAP_COUNT, count + article.claps)
            .addOnSuccessListener {
                Log.d(TAG, "Clap Count ++")
            }
            .addOnFailureListener {
                Log.e(TAG, "Clap Count ++ error ${it.localizedMessage}")
            }
    }

    override fun addComment(comment: String, pickedImage: Uri?, article: ArticleVO) {

        if (pickedImage != null) {
            uploadImageAndAddComment(comment, pickedImage, article)

        } else {
            val currentUser = UserAuthenticationModelImpl.currentUser!!
            val newComment = CommentVO(
                System.currentTimeMillis().toString(), "", comment, UserVO(
                    currentUser.providerId,
                    currentUser.displayName ?: "",
                    currentUser.photoUrl.toString())
            )
            addComment(newComment, article)
        }
    }

    private fun uploadImageAndAddComment(comment: String, pickedImage: Uri, article: ArticleVO) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesFolderRef = storageRef.child(STORAGE_FOLDER_PATH)


        val imageRef = imagesFolderRef.child(
            pickedImage.lastPathSegment ?: System.currentTimeMillis().toString()
        )

            val uploadTask = imageRef.putFile(pickedImage)

            uploadTask.addOnFailureListener{
                Log.e(TAG, it.localizedMessage)
            }
            .addOnSuccessListener {
                // get comment image's url

                imageRef.downloadUrl.addOnCompleteListener {
                    Log.d(TAG, "Image Uploaded ${it.result.toString()}")

                    val currentUser = UserAuthenticationModelImpl.currentUser!!
                    val newComment = CommentVO(
                        System.currentTimeMillis().toString(), it.result.toString(), comment,
                        UserVO(
                            currentUser.providerId,
                            currentUser.displayName ?: "",
                            currentUser.photoUrl.toString())
                    )

                    addComment(newComment, article)
                }

            }
    }

    private fun addComment(comment: CommentVO, article: ArticleVO){
        val commentsRef = fireStoreRef.collection(REF_PATH_ARTICLES).document(article.id).collection(REF_KEY_COMMENTS)

        val key = comment.id

        commentsRef.document(key).set(comment)
            .addOnSuccessListener {
                Log.d(TAG, "Add Comment")
            }
            .addOnFailureListener {
                Log.e(TAG, "Add Comment error ${it.localizedMessage}")
            }

    }
}