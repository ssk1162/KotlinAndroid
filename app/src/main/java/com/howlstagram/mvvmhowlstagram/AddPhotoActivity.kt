package com.howlstagram.mvvmhowlstagram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.firebase.storage.FirebaseStorage
import com.howlstagram.mvvmhowlstagram.databinding.ActivityAddPhotoBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddPhotoActivity : AppCompatActivity() {

    var photoUri : Uri? = null
    var photoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            photoUri = it.data?.data
            binding.uploadImageview.setImageURI(photoUri)
        }
    }

    lateinit var binding : ActivityAddPhotoBinding
    var storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_photo)
        binding.addphotoUploadBtn.setOnClickListener {
            contentUpload()
        }
        var i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        photoResult.launch(i)

    }

    fun contentUpload() {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + ".png"
        // images/IMAGE_123123.png
        var storagePath = storage.reference.child("images").child(imageFileName)

        // 구글에서 서버 과부화를 막기 위한 요청한 이미지만 Url를 만들어주는 기능
        storagePath.putFile(photoUri!!).continueWith {
            return@continueWith storagePath.downloadUrl
        }.addOnCompleteListener { downloadUrl ->
            Toast.makeText(this, "업로드 성공 : ${downloadUrl.result}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

}