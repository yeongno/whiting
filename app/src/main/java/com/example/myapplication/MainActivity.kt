package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.chatlist.ChatListFragment
import com.example.myapplication.firebase.LikeActivity
import com.example.myapplication.firebase.LoginActivity
//import com.example.myapplication.home.ContentsFragment
import com.example.myapplication.home.HomeFragment
import com.example.myapplication.home.ArticleFragment
import com.example.myapplication.home.ViewPagerFragment
import com.example.myapplication.interest.InterestFragement
import com.example.myapplication.map.MapFragment
import com.example.myapplication.mypage.MypageFragment
import com.example.myapplication.schedule.ScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //main
        val homeFragment = HomeFragment()
        val interestFragement = InterestFragement()
        val mapFragment = MapFragment()
        //val scheduleFragement = ScheduleFragment()
        val scheduleFragement = ChatListFragment()
        val mypageFragment = MypageFragment()

        //home
        /*
        val viewPagerFragment = ViewPagerFragment()
        val contentsFragment = ContentsFragment()
        val hotListFragment = ArticleFragment()*/

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home-> replaceFragment(homeFragment)
                R.id.interest-> replaceFragment(interestFragement)
                R.id.map-> replaceFragment(mapFragment)
                R.id.schedule-> replaceFragment(scheduleFragement)
                R.id.myPage-> replaceFragment(mypageFragment)

            }
            return@setOnNavigationItemReselectedListener
        }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }


    }
    fun setDataAtFragment(fragment:Fragment, title:String){
        val bundle = Bundle()
        bundle.putString("title", title)

        fragment.arguments = bundle
        setFragment(fragment)
    }

    fun setFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}