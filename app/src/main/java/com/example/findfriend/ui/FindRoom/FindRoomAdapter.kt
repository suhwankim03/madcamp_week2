package com.example.findfriend.ui.FindRoom


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.R

class FindRoomAdapter(private val myRoomList: MutableList<FindRoomDataModel>):
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
        val myRoomModel = myRoomList[position]


        holder.roomName.text = myRoomModel.roomName
        holder.limTime.text = myRoomModel.limTime.toString()
        holder.location.text = myRoomModel.location
        holder.currentPeople.text = myRoomModel.currentPeople.toString()
        holder.maxPeople.text = myRoomModel.maxPeople.toString()
        holder.owner.text = myRoomModel.ownerName
    }

    override fun getItemCount(): Int {
        return myRoomList.size
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.longClickListener = onItemLongClickListener
    }

    fun removeItem(position: Int) {
        myRoomList.removeAt(position)
        notifyItemRemoved(position)
    }
}