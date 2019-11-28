package com.padc.batch9.assignment13.mvp.view

import android.content.Intent
import com.google.firebase.auth.FirebaseUser

interface BaseGoogleSignInView: BaseView {

    fun navigateToGoogleSignInScreen(signInIntent: Intent, rcGoogleSign: Int)
    fun showGoogleLoginError(message: String)
    fun showGoogleLoginSuccess(user: FirebaseUser)
}