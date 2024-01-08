package com.example.findfriend.ui.FindRoom


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.JoinRoomActivity
import com.example.findfriend.R
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import java.sql.Date

class FindRoomAdapter(val findRoomList: MutableList<FindRoomDataModel?>):
    RecyclerView.Adapter<FindRoomAdapter.ViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
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
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myroom_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val findRoomModel = findRoomList[position]
        holder.roomID.text = findRoomModel?.roomId.toString()
        holder.roomName.text = findRoomModel?.roomName
        holder.limTime.text = findRoomModel?.limTime.toString()
        holder.location.text = findRoomModel?.location
        holder.currentPeople.text = findRoomModel?.minPeople.toString()
        holder.maxPeople.text = findRoomModel?.maxPeople.toString()
        holder.owner.text = findRoomModel?.owner

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, JoinRoomActivity::class.java )
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
        return findRoomList.size
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun removeItem(position: Int) {
        findRoomList.removeAt(position)
        notifyItemRemoved(position)
    }
}