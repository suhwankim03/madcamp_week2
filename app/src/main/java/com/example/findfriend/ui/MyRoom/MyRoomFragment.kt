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


        Log.d("tag","룸서비스(54행) 실행 완료")

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var roomService = retrofit.create(RoomService::class.java)

        Log.d("tag","룸서비스(62행) 실행 완료")

        //JSON list에서 데이터 받아와서 넣는 코드
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = roomService.getRoom().execute()
                    if (response.isSuccessful) {
                        val myRoom = response.body()
                        myRoomViewModel.addMyRoom(myRoom)
                        Log.d("73행","${myRoom}")
                        // 서버에서 받아온 데이터를 사용
                        withContext(Dispatchers.Main) {
                            // UI 업데이트 등을 수행
                        }
                    } else {
                        // 실패 시 처리
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}