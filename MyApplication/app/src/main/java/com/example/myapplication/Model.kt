package com.example.myapplication

data class Model(val name: String, val url: String) {
    fun getId(): String {
        return name
    }

}