package com.example.myapplication.map.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SearchResultEntity (
    val fullAdress: String,
    val name: String,
    val locationLatLng: LocationLatLngEntity
): Parcelable