package com.example.donatr.summary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donatr.R
import com.example.donatr.adapter.FirestoreAdapter
import com.example.donatr.adapter.TransactionAdapter
import com.example.donatr.data.Transaction
import com.example.donatr.databinding.ActivitySummaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TransactionAdapter(
            this,
            FirebaseAuth.getInstance().currentUser!!.uid
        )

        binding.recyclerTransactions.adapter = adapter

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        queryTransactions()

        binding.fab.setOnClickListener{
            finish()
        }
    }

    var snapshotListener: ListenerRegistration? = null

    fun queryTransactions() {
        val queryTransactions = FirestoreAdapter(this).getCollection(
            FirestoreAdapter.COLLECTION_TRANCS
        )

        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@SummaryActivity, getString(R.string.Error)+ "${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val transaction = docChange.document.toObject(Transaction::class.java)

                        if (transaction.uid == FirebaseAuth.getInstance().currentUser!!.uid) {
                            adapter.addTransaction(transaction)
                        }
                    }
                }
            }
        }

        snapshotListener = queryTransactions?.addSnapshotListener(eventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        snapshotListener?.remove()
    }
}