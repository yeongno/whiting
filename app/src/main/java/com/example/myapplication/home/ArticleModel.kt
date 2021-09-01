package com.example.myapplication.home

class ArticleModel (
    val sellerId: String,
    var name: String,
    var title: String,
    val createdAt: Long,
    val dataText: String,
    val imageUrl: String
    ){

    constructor(): this("","","",0,"","")

}