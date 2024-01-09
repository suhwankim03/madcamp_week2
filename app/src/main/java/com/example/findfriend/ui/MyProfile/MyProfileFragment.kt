package com.example.findfriend.ui.MyProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.Settings.GlobalApplication.Companion.prefs
import com.example.findfriend.databinding.FragmentMyprofileBinding
import com.example.findfriend.ui.Login.LoginActivity

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyprofileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myProfileViewModel =
            ViewModelProvider(this).get(MyProfileViewModel::class.java)

        _binding = FragmentMyprofileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.id.text = prefs.getString("email","이거 email 디폴트값임 나오면 오류임")
        binding.nickname.text = prefs.getString("nickname","이거 nickname 디폴트값임 나오면 오류임")

        binding.LogoutButton.setOnClickListener{
            prefs.deleteString("email")
            prefs.deleteString("nickname")

            val intent= Intent( activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}