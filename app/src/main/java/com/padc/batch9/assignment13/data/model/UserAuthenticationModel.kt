package com.padc.batch9.assignment13.data.model

import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface UserAuthenticationModel {

    val currentUser: FirebaseUser?

    fun isLoginUser(): Boolean

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onError: (String)->Unit): LiveData<FirebaseUser>


    fun logOut()


}