package com.example.myapplication.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val userDB = Firebase.database.reference.child("Users")

        initSignUpButton()
    }

    private fun getCurrentUserId(): String{
        return auth.currentUser?.uid.orEmpty()
    }

    private fun initSignUpButton(){

        val registerBtn = findViewById<Button>(R.id.btn_register)
        registerBtn.setOnClickListener {
            var email = getInputEmail()
            var password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        handlesuccessLogin()
                        startActivity(Intent(this,MainActivity::class.java))

                    } else {
                        Toast.makeText(
                            this,
                            "이미 가입한 이메일이거나, 회원가입에 실패했습니다. 로그인 버튼을 눌러 로그인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun getInputEmail(): String{
        val editId = findViewById<EditText>(R.id.edit_id)
        return editId.text.toString()
    }
    private fun getInputPassword(): String{
        val editPw = findViewById<EditText>(R.id.edit_pw)
        return editPw.text.toString()
    }

    private fun handlesuccessLogin(){
        if(auth.currentUser == null){
            Toast.makeText(this,"회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show()
            return
        }
        val name = findViewById<EditText>(R.id.name).text.toString()
        val password = findViewById<EditText>(R.id.edit_pw).text.toString()
        val userId =findViewById<EditText>(R.id.edit_id).text.toString()

        val currentUserDB = Firebase.database.reference.child("Users").child(auth.currentUser?.uid.orEmpty())
        //val currentUserDB = Firebase.database.reference.child("Users").child("userId")
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        user["name"] = name
        user["password"] = password

        currentUserDB.updateChildren(user)
    }

}