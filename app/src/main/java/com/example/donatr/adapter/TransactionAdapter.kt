package com.example.donatr.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.donatr.LoginActivity
import com.example.donatr.data.Charity
import com.example.donatr.data.Transaction
import com.example.donatr.data.User
import com.example.donatr.databinding.TransactionRowBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    lateinit var context: Context
    lateinit var currentUid: String

    var  transactionsList = mutableListOf<Transaction>()


    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TransactionRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return transactionsList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var transaction = transactionsList.get(holder.adapterPosition)
        holder.bind(transaction)
    }


    fun addTransaction(transaction: Transaction) {

        transactionsList.add(transaction)

        notifyItemInserted(transactionsList.lastIndex)
    }


    inner class ViewHolder(val binding: TransactionRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction) {
            val db = FirebaseFirestore.getInstance()

            db.collection(
                FirestoreAdapter.COLLECTION_CHARITIES
            ).document(transaction.cid).get()
                .addOnSuccessListener {
                    binding.tvCharity.text = it.toObject(Charity::class.java)!!.title
                }

            db.collection(
                FirestoreAdapter.COLLECTION_USERS
            ).whereEqualTo("uid", transaction.uid).get()
                .addOnSuccessListener {
                    binding.tvUser.text = it.documents[0].toObject(User::class.java)!!.userName
                }

            binding.tvAmount.text = transaction.amount.toString()
        }
    }
}