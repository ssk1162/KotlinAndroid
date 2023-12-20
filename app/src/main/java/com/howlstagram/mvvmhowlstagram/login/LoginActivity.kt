package com.howlstagram.mvvmhowlstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.howlstagram.mvvmhowlstagram.R
import com.howlstagram.mvvmhowlstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    //    lateinit var loginViewModel : LoginViewModel
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
//        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = loginViewModel

        binding.activity = this
        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        loginViewModel.showInputNumberActivity.observe(this) {
            if (it) {
                // 회원가입 시 돌아갈 필요가 없어서 finish
                finish()
                startActivity(Intent(this, InputNumberActivity::class.java))
            }
        }
        loginViewModel.showFindIdActivity.observe(this) {
            if (it) {
                startActivity(Intent(this, FindActivity::class.java))
            }
        }
    }

    fun findId() {
        println("finid")
        loginViewModel.showFindIdActivity.value = true
    }

    //  구글 로그인이 성공한 결과값 받는 함수
    var googleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            var data = result.data
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = task.getResult(ApiException::class.java)
            account.idToken // 로그인한 사용자 정보를 암호화한 값
            loginViewModel.firebaseAutWithGoogle(account.idToken)
        }

}