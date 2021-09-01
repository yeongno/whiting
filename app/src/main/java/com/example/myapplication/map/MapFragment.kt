package com.example.myapplication.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.map.model.LocationLatLngEntity
import com.example.myapplication.map.model.SearchResultEntity
import com.example.myapplication.map.response.search.Poi
import com.example.myapplication.map.response.search.Pois
import com.example.myapplication.map.utillity.RestrofitUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MapFragment : Fragment(R.layout.fragment_map), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var binding: FragmentMapBinding? = null
    private lateinit var adapter: SearchRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMapBinding = FragmentMapBinding.bind(view)
        binding = fragmentMapBinding

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()
        //setData(searchResponse.searchPoiInfo.pois.poi)
    }

    private fun initViews() = with(binding) {
        this!!.emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter

    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(pois: Pois) {
        val dataList = pois.poi.map {
            SearchResultEntity(
                name = it.name?:"빌딩명 없음",
                fullAdress = makeMainAdress(it),
                locationLatLng = LocationLatLngEntity(
                    it.noorLat,
                    it.noorLon
                )
            )
        }
        adapter.setSearchResultList(dataList) {
            Toast.makeText(activity, "빌딩이름 : ${it.name} 주소 : ${it.fullAdress} 위도: ${it.locationLatLng}", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun searchKeyword(ketwordString: String) {
        launch(coroutineContext) {
            try{
                withContext(Dispatchers.IO){
                    val response = RestrofitUtil.apiService.getSearchLocation(
                        keyword = ketwordString
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main){
                            Log.e("response", body.toString())
                            body?.let{ searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            }catch(e: Exception){
                e.printStackTrace()
                Toast.makeText(activity, "검색하는 과정에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeMainAdress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}