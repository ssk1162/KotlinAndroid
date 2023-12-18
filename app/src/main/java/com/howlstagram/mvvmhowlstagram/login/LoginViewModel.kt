package com.howlstagram.mvvmhowlstagram.login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.howlstagram.mvvmhowlstagram.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var auth = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient
    init {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun loginWithSignupEmail() {
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                showInputNumberActivity.value = true
            } else {
                // 아이디가 있을 때

            }
        }

    }

    fun loginGoogle(view : View) {
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)

    }

    fun firebaseAutWithGoogle(idToken : String?) {
        val creadential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(creadential).addOnCompleteListener {
            if (it.isSuccessful) {
                showInputNumberActivity.value = true
            } else {
                // 아이디가 있을 때

            }
        }
    }

}