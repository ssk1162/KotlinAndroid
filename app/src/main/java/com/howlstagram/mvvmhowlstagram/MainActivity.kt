package com.howlstagram.mvvmhowlstagram

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.howlstagram.mvvmhowlstagram.databinding.ActivityMainBinding
import com.howlstagram.mvvmhowlstagram.fragment.AlarmFragment
import com.howlstagram.mvvmhowlstagram.fragment.DetailViewFragment
import com.howlstagram.mvvmhowlstagram.fragment.GridFragment
import com.howlstagram.mvvmhowlstagram.fragment.UserFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 0)

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.action_home -> {
                    var f = DetailViewFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_search -> {
                    var f = GridFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_add_photo -> {
                    var f = GridFragment()
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        var i = Intent(this, AddPhotoActivity::class.java)
                        startActivity(i)
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.action_favorite_alarm -> {
                    var f = AlarmFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_account -> {
                    var f = UserFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,f).commit()
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener false
        }

    }
}