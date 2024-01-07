package com.example.findfriend.ui.MyRoom

import android.os.Bundle
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
import org.json.JSONArray
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

        //JSON list에서 데이터 받아와서 넣는 코드
        if (myRoomViewModel.getMyRoomList().isEmpty())
        {
            val inputStream = requireActivity().assets.open("myRoomList.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while(reader.readLine().also {line = it} != null)
            {
                stringBuilder.append(line)
            }
            reader.close()
            val jsonArrayString = stringBuilder.toString()
            val jsonArray = JSONArray(jsonArrayString)
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val roomID = jsonObject.getInt("roomID")
                val roomName = jsonObject.getString("roomName")
                val roomDetail = jsonObject.getString("roomDetail")
                val limTime = jsonObject.getInt("limTime")
                val location = jsonObject.getString("location")
                val maxPeople = jsonObject.getInt("maxPeople")
                val currentPeople = jsonObject.getInt("currentPeople")
                val ownerName = jsonObject.getString("ownerName")
                myRoomViewModel.addMyRoom(MyRoomDataModel(roomID, roomName, roomDetail, limTime, location, maxPeople, currentPeople, ownerName))
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}