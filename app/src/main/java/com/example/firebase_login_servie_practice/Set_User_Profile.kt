package com.example.firebase_login_servie_practice

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.firebase_login_servie_practice.databinding.ActivitySetUserProfileBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class Set_User_Profile : AppCompatActivity() {
    private lateinit var binding : ActivitySetUserProfileBinding
    var imgUri : Uri? = null
    var isFirst : Boolean = true
    var isChanged: Boolean = false
    var context: Context = this
    private lateinit var name: String
    private lateinit var profileUrL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadData()
        if(name != null){
            binding.userNAME.setText(name)
            Picasso.get().load(profileUrL).into(binding.userIMG)
            go_chat_room()
            isFirst = false
        }

        binding.userIMG.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, 10)
        }
        binding.enterChatListBTN.setOnClickListener {
            saveData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                imgUri = data?.data
                Picasso.get().load(imgUri).into(binding.userIMG)

                isChanged = true
            }
        }
    }

    private fun saveData() {
        name = binding.userNAME.text.toString()
        if(binding.userIMG == null) return
        var filename = SimpleDateFormat("yyyyMMddhhmmss").format(Date()) + ".png"
        val firebaseStorage = FirebaseStorage.getInstance()
        val imgRef: StorageReference = firebaseStorage.getReference("profileimages/" + filename)

        val uploadTask: UploadTask = imgRef.putFile(imgUri!!)
        uploadTask.addOnSuccessListener ( object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                    imgRef.getDownloadUrl().addOnSuccessListener( object : OnSuccessListener<Uri> {
                         override fun onSuccess(uri: Uri) {
                            profileUrL = uri.toString()
                            Toast.makeText(context, "프로필 저장 완료", Toast.LENGTH_SHORT).show()
                            val firebaseDataBase = FirebaseDatabase.getInstance()
                            val profileRef: DatabaseReference = firebaseDataBase.getReference("profiles")

                            profileRef.child(name).setValue(profileUrL)

                            val Preferences: SharedPreferences = getSharedPreferences("account", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = Preferences.edit()

                            editor.putString("nickName", name)
                            editor.putString("profileUrl", profileUrL)

                            editor.commit()
                            go_chat_room()
                            finish()
                        }
                    })
                }
            })
        }
    private fun loadData() {
        val preferences: SharedPreferences = getSharedPreferences("account", MODE_PRIVATE)
        name = preferences.getString("nickName", null).toString()
        profileUrL = preferences.getString("profileUrl", null).toString()
    }

    private fun go_chat_room (){
        val go_chat_rooom = Intent(context, Chat_Room_List::class.java)
        startActivity(go_chat_rooom)
    }
}

