package com.example.donatr.data

data class User(
    var uid: String = "",
    var userName: String = "",
    var balance: Int = 0,
    var transactionHistory: MutableList<Transaction> = mutableListOf()
)
