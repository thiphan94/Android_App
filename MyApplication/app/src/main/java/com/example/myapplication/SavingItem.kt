package com.example.myapplication

//for read and write savings data with Firestore
data class SavingItem(
    var date: String = "",
    var amount: Double = 0.0
)
