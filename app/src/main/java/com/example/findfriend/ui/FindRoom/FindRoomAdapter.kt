package com.example.findfriend.ui.FindRoom


import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.R
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import java.sql.Date

class FindRoomAdapter(private val findRoomList: MutableList<FindRoomDataModel?>):
    RecyclerView.Adapter<FindRoomAdapter.ViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    private var longClickListener: OnItemLongClickListener? = null
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onLongClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val roomName: TextView = itemView.findViewById(R.id.roomName)
        val limTime: TextView = itemView.findViewById(R.id.limTime)
        val location: TextView = itemView.findViewById(R.id.location)
        val currentPeople: TextView = itemView.findViewById(R.id.currentNumber)
        val maxPeople: TextView = itemView.findViewById(R.id.maxNumber)
        val owner: TextView = itemView.findViewById(R.id.owner)

        //val roomID: ImageView = itemView.findViewById(R.id.gallery_imageView)
        //val roomDetail: ImageView = itemView.findViewById(R.id.gallery_imageView)
        //val minPeople: ImageView = itemView.findViewById(R.id.gallery_imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myroom_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val findRoomModel = findRoomList[position]
        holder.roomName.text = findRoomModel?.roomName
        holder.limTime.text = findRoomModel?.limTime.toString()
        holder.location.text = findRoomModel?.location
        holder.currentPeople.text = findRoomModel?.minPeople.toString()
        holder.maxPeople.text = findRoomModel?.maxPeople.toString()
        holder.owner.text = findRoomModel?.owner
    }

    override fun getItemCount(): Int {
        return findRoomList.size
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.longClickListener = onItemLongClickListener
    }

    fun removeItem(position: Int) {
        findRoomList.removeAt(position)
        notifyItemRemoved(position)
    }
}