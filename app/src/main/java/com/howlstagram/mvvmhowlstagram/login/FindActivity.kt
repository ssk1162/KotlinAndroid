package com.howlstagram.mvvmhowlstagram.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.howlstagram.mvvmhowlstagram.R
import com.howlstagram.mvvmhowlstagram.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {

    lateinit var binding : ActivityFindBinding
    val findIdViewModel : FindIdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find)
        binding.activity = this
        binding.viewModel = findIdViewModel
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve() {
        findIdViewModel.toastMessage.observe(this) {
            if (!it.isEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}