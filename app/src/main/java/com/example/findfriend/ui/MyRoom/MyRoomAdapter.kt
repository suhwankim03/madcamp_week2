package com.example.findfriend.ui.MyRoom


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.ChatActivity
import com.example.findfriend.JoinRoomActivity
import com.example.findfriend.R
import com.example.findfriend.ui.FindRoom.FindRoomAdapter
import java.sql.Date

class MyRoomAdapter(val myRoomList: MutableList<MyRoomDataModel?>):
    RecyclerView.Adapter<MyRoomAdapter.ViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    private var itemLongClickListener : OnItemLongClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int):Boolean
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val roomID: TextView = itemView.findViewById(R.id.roomID)
        //val roomDetail: ImageView = itemView.findViewById(R.id.gallery_imageView)
        val roomName: TextView = itemView.findViewById(R.id.roomName)
        val limTime: TextView = itemView.findViewById(R.id.limTime)
        val location: TextView = itemView.findViewById(R.id.location)
        val currentPeople: TextView = itemView.findViewById(R.id.currentNumber)
        val maxPeople: TextView = itemView.findViewById(R.id.maxNumber)
        val owner: TextView = itemView.findViewById(R.id.owner)

        init{
            itemView.setOnClickListener{
                itemClickListener?.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                val result = itemLongClickListener?.onItemLongClick(adapterPosition) == true
                if (result) {
                    Log.d("MyRoomAdapter", "Long Clicked: Position $adapterPosition")
                }
                result
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myroom_cardview, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyRoomAdapter.ViewHolder, position: Int) {
        val myRoomModel = myRoomList[position]
        holder.roomID.text = myRoomModel?.roomId.toString()
        holder.roomName.text = myRoomModel?.roomName
        holder.limTime.text = myRoomModel?.limTime.toString()
        holder.location.text = myRoomModel?.location
        holder.currentPeople.text = myRoomModel?.minPeople.toString()
        holder.maxPeople.text = myRoomModel?.maxPeople.toString()
        holder.owner.text = myRoomModel?.owner

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, ChatActivity::class.java )
            intent.putExtra("roomID","${holder.roomID.text}")
            intent.putExtra("roomName","${holder.roomName.text}")
            intent.putExtra("limTime","${holder.limTime.text}")
            intent.putExtra("location","${holder.location.text}")
            intent.putExtra("currentPeople","${holder.currentPeople.text}")
            intent.putExtra("maxPeople","${holder.maxPeople.text}")
            intent.putExtra("owner","${holder.owner.text}")
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }


    }

    override fun getItemCount(): Int {
        return myRoomList.size
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.itemLongClickListener = onItemLongClickListener
    }

    fun removeItem(position: Int) {
        myRoomList.removeAt(position)
        notifyItemRemoved(position)
    }
}