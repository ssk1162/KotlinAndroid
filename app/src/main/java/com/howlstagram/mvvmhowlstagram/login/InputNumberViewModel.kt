package com.howlstagram.mvvmhowlstagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class FindIdModel(var id : String? = null, var phoneNumber : String? = null)
class InputNumberViewModel : ViewModel() {

    // 계정 관리
    var auth = FirebaseAuth.getInstance()
    // DB 관리
    var firestore = FirebaseFirestore.getInstance()
    // 다음 페이지로 넘거가는 플레그 값
    var nextPage = MutableLiveData(false)
    // 폰 번호는 받을 수 있는 변수
    var inputNumber = ""

    // 폰 번호를 저장하는 함수
    fun savePhoneNumber() {
        // 이메일과 아이디를 가지고있는 변수 선언
        var findIdModel = FindIdModel(auth.currentUser?.email, inputNumber)
        // 저장적으로 db에 들어 갔을대 다음 페이지로 넘어감
        firestore.collection("findIds").document().set(findIdModel).addOnCompleteListener {
            if (it.isSuccessful) {
                nextPage.value = true
                // 이메일이 유효한지 인증메일을 보낼 수 있음
                // 이메일을 무작위로 넣을 수 없도록 하는 기능
                auth.currentUser?.sendEmailVerification()
            }
        }
    }

}