package com.example.findfriend.ui.MyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.databinding.FragmentMyprofileBinding

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyprofileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MyProfileViewModel::class.java)

        _binding = FragmentMyprofileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.myprofileText
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = GlobalApplication.prefs.getString("nickname","이거 디폴트값임 나오면 오류임")
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}