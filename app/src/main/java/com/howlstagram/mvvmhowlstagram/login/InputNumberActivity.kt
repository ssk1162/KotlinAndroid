package com.howlstagram.mvvmhowlstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.howlstagram.mvvmhowlstagram.R
import com.howlstagram.mvvmhowlstagram.databinding.ActivityInputNumberBinding

class InputNumberActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputNumberBinding
    val inputViewModel : InputNumberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_number)
        binding.viewModel = inputViewModel
        binding.lifecycleOwner = this
        setObserve()

    }

    fun setObserve() {
        inputViewModel.nextPage.observe(this) {
            // 이메일 인증이된 사용자만 로그인 될 수 있게 로그인엑티비티로 이동
            if (it) {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

}