package com.example.findfriend.ui.MyRoom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.CreateAccountActivity
import com.example.findfriend.GlobalApplication.Companion.prefs
import com.example.findfriend.MainActivity
import com.example.findfriend.R
import com.example.findfriend.databinding.FragmentMyroomBinding
import com.example.findfriend.ui.RoomService
import com.example.findfriend.ui.Signup
import com.example.findfriend.ui.SignupResponse
import com.example.findfriend.ui.SignupService
import com.example.findfriend.ui.addRoom
import com.example.findfriend.ui.addRoomResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader

class MyRoomFragment : Fragment() {

    private var _binding: FragmentMyroomBinding? = null
    private lateinit var myRoomAdapter: MyRoomAdapter
    private lateinit var myRoomRecyclerView: RecyclerView
    private lateinit var myRoomViewModel : MyRoomViewModel
    private lateinit var addbutton : Button

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

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var roomService = retrofit.create(RoomService::class.java)

        Log.d("tag","룸서비스(62행) 실행 완료")
        binding.swipeLayout.setOnRefreshListener {
            val id = prefs.getString("email","email 검색 오류") // 이거 나중에 사용자 아이디를 받아와야 함.
            roomService.getRoom2(id).enqueue(object : Callback<List<MyRoomDataModel>> {
                override fun onResponse(call: Call<List<MyRoomDataModel>>, response: Response<List<MyRoomDataModel>>) {
                    Log.d("69행","${response}")
                    if (response.isSuccessful) {
                        val myRoom = response.body()
                        Log.d("73행","${myRoom}")


                        myRoomViewModel.clearFindRoomList()
                        for (i in 0 until myRoom!!.size){
                            val roomID = myRoom[i].roomId
                            val roomName = myRoom[i].roomName
                            val roomDetail = myRoom[i].roomDetail
                            val limTime = myRoom[i].limTime
                            val location = myRoom[i].location
                            val maxPeople = myRoom[i].maxPeople
                            val currentPeople = myRoom[i].minPeople
                            val ownerName = myRoom[i].owner
                            myRoomViewModel.addMyRoom(MyRoomDataModel(roomID, roomName, roomDetail, limTime, location, maxPeople, currentPeople, ownerName))
                            Log.d("79행","${myRoomViewModel.getMyRoomList()}")
                        }
                        binding.swipeLayout.isRefreshing = false
                    } else {
                        // 서버 응답이 실패했을 때의 처리
                        binding.swipeLayout.isRefreshing = false

        roomService.getRoom2(id).enqueue(object : Callback<List<MyRoomDataModel>> {
            override fun onResponse(call: Call<List<MyRoomDataModel>>, response: Response<List<MyRoomDataModel>>) {
                Log.d("69행","${response}")
                if (response.isSuccessful) {
                    val myRoom = response.body()
                    Log.d("73행","${myRoom}")
                    for (i in 0 until myRoom!!.size){
                        val roomID = myRoom[i].roomId
                        val roomName = myRoom[i].roomName
                        val roomDetail = myRoom[i].roomDetail
                        val limTime = myRoom[i].limTime
                        val location = myRoom[i].location
                        val maxPeople = myRoom[i].maxPeople
                        val currentPeople = myRoom[i].minPeople
                        val ownerName = myRoom[i].owner
                        myRoomViewModel.addMyRoom(MyRoomDataModel(roomID, roomName, roomDetail, limTime, location, maxPeople, currentPeople, ownerName))
                        Log.d("79행","${myRoomViewModel.getMyRoomList()}")
                    }
                }

                override fun onFailure(call: Call<List<MyRoomDataModel>>, t: Throwable) {
                    // 네트워크 요청 자체가 실패했을 때의 처리
                    binding.swipeLayout.isRefreshing = false
                }
            })
        }


        //방 생성 버튼 관련 로직 (전체 방 만들기 추가
        addbutton=binding.plusbutton

        addbutton.setOnClickListener{
            val newRoom = addRoom(roomName = "1", roomDetail = "1", limTime = 0, location = "1", maxPeople = 0, minPeople = 0, owner = "1")

            roomService.requestAddRoom(newRoom).enqueue(object: Callback<addRoomResponse>{
                override fun onResponse(
                    call: Call<addRoomResponse>,
                    response: Response<addRoomResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            Toast.makeText(requireContext(), "방 생성!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "방 생성 실패!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<addRoomResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }
            })


        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}