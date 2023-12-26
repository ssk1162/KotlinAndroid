package com.howlstagram.mvvmhowlstagram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.howlstagram.mvvmhowlstagram.databinding.ActivityAddPhotoBinding
import com.howlstagram.mvvmhowlstagram.model.ContentModel
import java.text.SimpleDateFormat
import java.util.Date

class AddPhotoActivity : AppCompatActivity() {

    var photoUri: Uri? = null
    var photoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            photoUri = it.data?.data
            binding.uploadImageview.setImageURI(photoUri)
        }
    }

    lateinit var binding: ActivityAddPhotoBinding
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
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
        storagePath.putFile(photoUri!!).continueWithTask {
            return@continueWithTask storagePath.downloadUrl
        }.addOnCompleteListener { downloadUrl ->
            var contentModel = ContentModel()
            contentModel.imageUrl = downloadUrl.result.toString()
            contentModel.explain = binding.addphotoEditEdittext.text.toString()
            contentModel.uid = auth.uid
            contentModel.userId = auth.currentUser?.email
            contentModel.timestamp = System.currentTimeMillis()

            firestore.collection("images").document().set(contentModel).addOnCompleteListener {
                Toast.makeText(this, "업로드 성공", Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }

}