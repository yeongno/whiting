/*package com.example.myapplication.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentContentsBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.home.Contents.OsoonAdapter
import com.example.myapplication.home.Contents.OsoonModel

class ContentsFragment: Fragment(R.layout.fragment_contents) {
    private var binding: FragmentContentsBinding? = null
    private lateinit var osoonAdapter: OsoonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentContentsBinding = FragmentContentsBinding.bind(view)
        binding = fragmentContentsBinding

        osoonAdapter = OsoonAdapter()
        osoonAdapter.submitList(mutableListOf<OsoonModel>().apply{
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
            add(OsoonModel("0","aaaa",1000000, "5000원",""))
        })

        fragmentContentsBinding.osoonlayout.layoutManager = LinearLayoutManager(context)
        fragmentContentsBinding.osoonlayout.adapter = osoonAdapter


        }

    }

*/