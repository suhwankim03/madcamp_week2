package com.example.findfriend.ui.MyRoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.Settings.GlobalApplication.Companion.prefs
import com.example.findfriend.Settings.GlobalApplication.Companion.roomService
import com.example.findfriend.databinding.FragmentMyroomBinding
import com.example.findfriend.connectDB.RoomService
import com.example.findfriend.connectDB.delete
import com.example.findfriend.connectDB.deleteResponse
import com.example.findfriend.connectDB.withdraw
import com.example.findfriend.connectDB.withdrawResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRoomFragment : Fragment() {

    private var _binding: FragmentMyroomBinding? = null
    private lateinit var myRoomAdapter: MyRoomAdapter
    private lateinit var myRoomRecyclerView: RecyclerView
    private lateinit var myRoomViewModel : MyRoomViewModel
    private val id = prefs.getString("email","email 검색 오류")
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myRoomViewModel = ViewModelProvider(this).get(MyRoomViewModel::class.java)
        _binding = FragmentMyroomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        myRoomRecyclerView = binding.myRoomView
        myRoomRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        myRoomAdapter = MyRoomAdapter(myRoomViewModel.getMyRoomList())
        myRoomRecyclerView.adapter = myRoomAdapter

        myRoomAdapter.setItemLongClickListener(object : MyRoomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int): Boolean {
                showDeleteDialog(position)
                return true
            }
        })

        UpdateUI()
        binding.swipeLayout.setOnRefreshListener {
            UpdateUI()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun UpdateUI(){
        roomService.getMyRoom(id).enqueue(object : Callback<List<MyRoomDataModel>> {
            override fun onResponse(
                call: Call<List<MyRoomDataModel>>,
                response: Response<List<MyRoomDataModel>>
            ) {
                if (response.isSuccessful) {
                    val myRoom = response.body()
                    myRoomViewModel.clearMyRoomList()
                    for (i in 0 until myRoom!!.size) {
                        val roomID = myRoom[i].roomId
                        val roomName = myRoom[i].roomName
                        val roomDetail = myRoom[i].roomDetail
                        val limTime = myRoom[i].limTime
                        val location = myRoom[i].location
                        val maxPeople = myRoom[i].maxPeople
                        val currentPeople = myRoom[i].minPeople
                        val ownerName = myRoom[i].owner
                        myRoomViewModel.addMyRoom(MyRoomDataModel(roomID, roomName, roomDetail, limTime, location, maxPeople, currentPeople, ownerName))
                    }
                    myRoomAdapter.notifyDataSetChanged()
                    binding.swipeLayout.isRefreshing = false

                } else {
                    // 서버 응답이 실패했을 때의 처리
                    binding.swipeLayout.isRefreshing = false
                }
            }
            override fun onFailure(call: Call<List<MyRoomDataModel>>, t: Throwable) {
                // 네트워크 요청 자체가 실패했을 때의 처리
                binding.swipeLayout.isRefreshing = false
            }
        })
    }
    private fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("파티 탈퇴")
        builder.setMessage("파티에서 나가시겠습니까?/n 방장이 나갈시 파티는 사라집니다.")

        builder.setPositiveButton("나가기") { _, _ ->
            val thisRoom = myRoomViewModel.getMyRoom(position)

            val roomID = thisRoom?.roomId
            val roomOwner = thisRoom?.owner

            if (id==roomOwner){ //방장이면 방 자체를 삭제
                deleteRoom(roomID)
            } else{ //방장이 아니면 단순 나가기
                withdrawRoom(roomID)
            }

            UpdateUI()
        }

        builder.setNegativeButton("취소") { _, _ ->
            // 사용자가 취소를 선택한 경우 아무것도 하지 않음
        }

        builder.show()
    }
    private fun withdrawRoom(roomID: Int?){
        val withdraw = withdraw(roomId = roomID, myId = id)
        roomService.requestWithdraw(withdraw).enqueue(object: Callback<withdrawResponse> {
            override fun onResponse(
                call: Call<withdrawResponse>,
                response: Response<withdrawResponse>
            ) {
                val successValue = response.body()
                if (successValue != null) {
                    val issuc = successValue.success
                    if (issuc) {
                        //성공시 리턴하고 싶으면 여기 적기
                    } else {
                        //실패시 리턴하고 싶으면 여기 적기
                    }
                }
            }
            override fun onFailure(call: Call<withdrawResponse>, t: Throwable) {
                Log.e("API Request", "Error: ${t.message}")
            }
        })
    }

    private fun deleteRoom(roomID: Int?){
        val delete = delete(roomId = roomID, myId = id)
        roomService.requestDelete(delete).enqueue(object: Callback<deleteResponse> {
            override fun onResponse(
                call: Call<deleteResponse>,
                response: Response<deleteResponse>
            ) {
                val successValue = response.body()
                if (successValue != null) {
                    val issuc = successValue.success
                    if (issuc) {
                        //성공시 리턴하고 싶으면 여기 적기
                    } else {
                        //실패시 리턴하고 싶으면 여기 적기
                    }
                }
            }
            override fun onFailure(call: Call<deleteResponse>, t: Throwable) {
                Log.e("API Request", "Error: ${t.message}")
            }
        })
    }
}