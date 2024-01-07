package com.example.findfriend.ui.FindRoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findfriend.databinding.FragmentFindroomBinding
import com.example.findfriend.ui.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindRoomFragment : Fragment() {

    private var _binding: FragmentFindroomBinding? = null
    private lateinit var findRoomAdapter: FindRoomAdapter
    private lateinit var findRoomRecyclerView: RecyclerView
    private lateinit var findRoomViewModel : FindRoomViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        findRoomViewModel = ViewModelProvider(this).get(FindRoomViewModel::class.java)
        _binding = FragmentFindroomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        findRoomRecyclerView = binding.findRoomView
        findRoomRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        findRoomAdapter = FindRoomAdapter(findRoomViewModel.getFindRoomList())
        findRoomRecyclerView.adapter = findRoomAdapter

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var roomService = retrofit.create(RoomService::class.java)

        Log.d("tag","룸서비스(62행) 실행 완료")


        val id = "asd" // 이거 나중에 사용자 아이디를 받아와야 함.
        roomService.getRoom().enqueue(object : Callback<List<FindRoomDataModel>> {
            override fun onResponse(call: Call<List<FindRoomDataModel>>, response: Response<List<FindRoomDataModel>>) {
                Log.d("69행","${response}")
                if (response.isSuccessful) {
                    val findRoom = response.body()
                    Log.d("73행","${findRoom}")
                    findRoomViewModel.clearFindRoomList()
                    for (i in 0 until findRoom!!.size){
                        val roomID = findRoom[i].roomId
                        val roomName = findRoom[i].roomName
                        val roomDetail = findRoom[i].roomDetail
                        val limTime = findRoom[i].limTime
                        val location = findRoom[i].location
                        val maxPeople = findRoom[i].maxPeople
                        val currentPeople = findRoom[i].minPeople
                        val ownerName = findRoom[i].owner
                        findRoomViewModel.addMyRoom(FindRoomDataModel(roomID, roomName, roomDetail, limTime, location, maxPeople, currentPeople, ownerName))
                        Log.d("79행","${findRoomViewModel.getFindRoomList()}")
                    }
                } else {
                    // 서버 응답이 실패했을 때의 처리
                }
            }

            override fun onFailure(call: Call<List<FindRoomDataModel>>, t: Throwable) {
                // 네트워크 요청 자체가 실패했을 때의 처리
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}