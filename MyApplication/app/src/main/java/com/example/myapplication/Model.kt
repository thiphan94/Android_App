package com.example.myapplication

// for spinner of categories in Fragment 2
data class Model(val name: String, val url: String) {
    fun getId(): String {
        return name
    }

}