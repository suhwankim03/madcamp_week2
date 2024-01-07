package com.example.findfriend.ui.MyRoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.R
import com.example.findfriend.databinding.FragmentMyroomBinding
import com.example.findfriend.ui.RoomService
import com.example.findfriend.ui.SignupService
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
            roomService.getRoom().enqueue(object : Callback<List<MyRoomDataModel>> {
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
                    }
                }

                override fun onFailure(call: Call<List<MyRoomDataModel>>, t: Throwable) {
                    // 네트워크 요청 자체가 실패했을 때의 처리
                    binding.swipeLayout.isRefreshing = false
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