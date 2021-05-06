package com.example.firebase_login_servie_practice

import android.app.Application
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Chat_Room_RCV_Adapter (val items: MutableList<chat_room_item>, val context: Context) : RecyclerView.Adapter<Chat_Room_RCV_Adapter.ViewHolder> (){
    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){

        var room_id : Int? = null
        var room_img: ImageView = view.findViewById(R.id.room_item_IMG)
        var room_name: TextView = view.findViewById(R.id.room_item_NAME)
        fun bind(item: chat_room_item){
            room_id = item.ROOM_ID
            room_name.text = item.ROOM_NAME
            if(item.ROOM_IMG == null){
                Glide.with(context).load(R.drawable.ic_launcher_foreground).into(room_img)
            }
            else{
                Glide.with(context).load(item.ROOM_IMG).into(room_img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_room_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = items.size
}