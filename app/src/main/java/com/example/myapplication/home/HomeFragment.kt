package com.example.myapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.chatdetail.ChatRoomActivity
import com.example.myapplication.chatlist.ChatListAdapter
import com.example.myapplication.chatlist.ChatListItem
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.home.Contents.ContentsFragment2
import com.example.myapplication.mypage.DBKey
import com.example.myapplication.mypage.DBKey.Companion.CHILD_CHAT
import com.example.myapplication.mypage.DBKey.Companion.DB_ARTICLES
import com.example.myapplication.mypage.DBKey.Companion.DB_USERS
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter

    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    private var binding: FragmentHomeBinding? = null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        articleList.clear()
        userDB = Firebase.database.reference.child(DB_USERS)
        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        //TODO 관심버튼 누르면 INTEREST에 추가

        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            if (auth.currentUser != null) {
                // 로그인을 한 상태
                if (auth.currentUser?.uid != articleModel.sellerId) {

                    val chatRoom = ChatListItem(
                        buyerId = auth.currentUser?.uid.orEmpty(),
                        sellerId = articleModel.sellerId,
                        itemTitle = articleModel.title,
                        key = System.currentTimeMillis()
                    )

                    userDB.child(auth.currentUser?.uid.orEmpty())
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    userDB.child(articleModel.sellerId)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)


                    Snackbar.make(view, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG).show()


                } else {
                    // 내가 올린 아이템
                    Snackbar.make(view, "내가 올린 아이템입니다", Snackbar.LENGTH_LONG).show()
                }
            } else {
                // 로그인을 안한 상태
                Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
            }


        })

        //       articleAdapter.submitList(mutableListOf<ArticleModel>().apply{
        //          add(ArticleModel("0","aaaa",1000000, "5000원","")) })

        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            // 해당 리스트로 이동
            //todo 해당콘텐츠의 fragment로 이동 *수정
            context?.let {
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", articleModel.createdAt)
                startActivity(intent)
            }
        })
        fragmentHomeBinding.articlelayout.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articlelayout.adapter = articleAdapter

        fragmentHomeBinding.addFloatingButton.setOnClickListener {
            context?.let {
                val intent = Intent(it, AddArticleActivity::class.java)
                startActivity(intent)
            }
        }

        articleDB.addChildEventListener(listener)


        /*fragmentHomeBinding.HomeSearch.setOnClickListener {
            context?.let{
                val intent = Intent(it, AddActivity::class.java)
                startActivity(intent)
            }*/
        fragmentHomeBinding.data1.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setFragment(ContentsFragment2())
        }
        fragmentHomeBinding.data2.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setFragment(ContentsFragment2())
        }
    }
            override fun onResume() {
                super.onResume()

                articleAdapter.notifyDataSetChanged()
            }

            override fun onDestroy() {
            super.onDestroy()

            articleDB.removeEventListener(listener)
        }

    }









