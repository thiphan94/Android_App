package com.example.myapplication

data class CustomClass(
        var name: String = "",
        var id: Int = 0

){

    override fun toString(): String {
        return name
    }


}
