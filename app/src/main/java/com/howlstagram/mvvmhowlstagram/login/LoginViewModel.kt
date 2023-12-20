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

    // 파이어베이스의 Authentication 인스턴스를 가져온다
    var auth = FirebaseAuth.getInstance()

    var id: MutableLiveData<String> = MutableLiveData("howl")
    var password: MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity: MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity: MutableLiveData<Boolean> = MutableLiveData(false)
    var context = getApplication<Application>().applicationContext

    // 구글 로그인 클라이언트를 초기화
    var googleSignInClient: GoogleSignInClient

    init {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // 회원가입
    fun loginWithSignupEmail() {
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) { // 회원가입 성공 시 화면이 넘어감
                    showInputNumberActivity.value = true
                } else {
                    // 아이디가 있을 때

                }
            }

    }

    fun loginGoogle(view: View) {
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
    }

    // 로그인 성공 시 파이어베이스에 등록
    fun firebaseAutWithGoogle(idToken: String?) {
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