package com.example.myapplication.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentMypageBinding
import com.example.myapplication.firebase.LoginActivity

class MypageFragment : Fragment(R.layout.fragment_mypage) {

    private var binding: FragmentMypageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMyPageBinding = FragmentMypageBinding.bind(view)
        binding = fragmentMyPageBinding

        val LogOut = fragmentMyPageBinding.LogOutButton
        LogOut.setOnClickListener {
            //todo currentuserId false 초기화
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() = with(binding) {
    }


}