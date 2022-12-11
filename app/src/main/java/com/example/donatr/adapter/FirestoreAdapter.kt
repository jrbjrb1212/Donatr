package com.example.donatr.adapter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreAdapter {

    companion object {
        const val COLLECTION_USERS = "users"
        const val COLLECTION_TRANCS = "transactions"
        const val COLLECTION_CHARITIES = "charities"
    }

    lateinit var context: Context
    var db = FirebaseFirestore.getInstance()

    constructor(context: Context) {
        this.context = context
    }


    fun addToCollection(collection: String, document: Any) {
        val collection = db.collection(collection)
        collection.add(document)
            .addOnFailureListener {
                Toast.makeText(
                    this.context,
                    "Error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    fun getCollection(collection: String): CollectionReference? {
        return db.collection(collection)
    }

}