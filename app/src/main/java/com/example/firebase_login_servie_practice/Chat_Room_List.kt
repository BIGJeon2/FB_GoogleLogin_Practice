package com.example.firebase_login_servie_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase_login_servie_practice.databinding.ActivityChatRoomListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Chat_Room_List : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivityChatRoomListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = Firebase.database.reference

        binding.logOutFloatBtn.setOnClickListener { auth.signOut() }
    }
}