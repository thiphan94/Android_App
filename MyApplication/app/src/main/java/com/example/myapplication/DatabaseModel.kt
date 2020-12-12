package com.example.myapplication

class DatabaseModel {
    var id = ""
    var type =""
    var date =""
    var amount = 0.0
    //var category =""
    //constructor(type:String,date:String,amount:Double,category:String){
    constructor(id: String, type:String,date:String, amount:Double){
        this.id = id
        this.type = type
        this.date = date
        this.amount = amount
        //this.category = category
    }
}