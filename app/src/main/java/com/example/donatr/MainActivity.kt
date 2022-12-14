package com.example.donatr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.donatr.databinding.ActivityMainBinding
import com.example.donatr.adapter.FirestoreAdapter
import com.example.donatr.data.Charity
import com.example.donatr.data.User
import com.example.donatr.summary.MoreInfoDialog
import com.example.donatr.summary.SummaryActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.bumptech.glide.Glide
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var binding: ActivityMainBinding

    private var charities: MutableList<Charity> = mutableListOf()
    private var charityIndex = 0

    var x2 : Float = 0.0f
    var x1 : Float = 0.0f
    var y2 : Float = 0.0f
    var y1 : Float = 0.0f

    companion object {
        var available_balance: Double = 0.0
        const val MIN_DISTANCE = 150
        var swipeCost = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUserBalance()
        getCharities()

        mainActivityBtnBindingsInit()

        gestureDetector = GestureDetector(this,this)
    }


    private fun mainActivityBtnBindingsInit() {
        binding.btnMore.setOnClickListener{
            onDownSwipe()
        }

        mainActivityBtnBindingsInitBottomBtns()
        mainActivityBtnBindingsInitFABS()
    }


    private fun mainActivityBtnBindingsInitBottomBtns(){
        binding.btnTransHist.setOnClickListener {
            val intent = Intent(applicationContext, SummaryActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddFunds.setOnClickListener {
            fundAddDialog(getString(R.string.addFundsBasic))
        }
    }

    private fun mainActivityBtnBindingsInitFABS(){
        binding.fabSwipeLeft.setOnClickListener{
            leftSwipe()
        }

        binding.fabSwipeRight.setOnClickListener{
            rightSwipe()
        }
    }


    fun getCharities() {
        Log.d("qwer", "getChar")
        FirestoreAdapter(this).getCollection(
            FirestoreAdapter.COLLECTION_CHARITIES
        )!!.get()
            .addOnSuccessListener {
                it.documents.forEach{
                    charities.add(it.toObject(Charity::class.java)!!)
                }

                firstLoadCardDetails()
            }
    }


    fun updateUserBalance() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirestoreAdapter(this).getCollection(
            FirestoreAdapter.COLLECTION_USERS
        )!!.whereEqualTo("uid", uid).get()
            .addOnSuccessListener {
                MainActivity.available_balance = it.documents[0].toObject(User::class.java)!!.balance
                updateShown()
            }
    }


    fun changeUserBalance(newBalance: Double) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val collection = FirestoreAdapter(this).getCollection(
            FirestoreAdapter.COLLECTION_USERS
        )!!

        collection.whereEqualTo("uid", uid).get()
            .addOnSuccessListener {
                collection.document(it.documents[0].id).update(
                    "balance", newBalance
                )
                updateUserBalance()
            }
    }


    private fun fundAddDialog(contextType: String) {
        val fundDialog = FundAddDiag(contextType)
        fundDialog.show(supportFragmentManager, getString(R.string.addFundsBasicv2))
    }


    fun updateShown(){
        binding.tvBalance.text = getString(R.string.dollarSign) + "$available_balance"
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)
        swipeActionListener(event)

        return super.onTouchEvent(event)
    }


    private fun swipeActionListener(event: MotionEvent?) {
        when (event?.action){
            0 -> {
                x1 = event.x
                y1 = event.y
            }

            1 -> {
                x2 = event.x
                y2 = event.y
                detectHorizontalSwipe(x1, x2, y1, y2)
            }
        }
    }


    private fun detectHorizontalSwipe(x1: Float, x2: Float, y1: Float, y2: Float) {
        val valueX : Float = x2-x1
        val valueY : Float = y2-y1

        if(kotlin.math.abs(valueX) > MIN_DISTANCE) horizontalSwipeHandle()
        if (kotlin.math.abs(valueY) > MIN_DISTANCE) verticalSwipeHandle()
    }


    private fun verticalSwipeHandle() {
        if(y2 > y1){
            onDownSwipe()
        }
    }


    private fun horizontalSwipeHandle() {
        // right swipe
        if (x2 > x1) rightSwipe()
        // left swipe
        else leftSwipe()
    }


    private fun leftSwipe() {
        if (available_balance >= swipeCost) leftAnimation()

        Timer("Update", true).schedule(300){
            runOnUiThread{
                val withUpdateBalance = false
                updateCardDetails(withUpdateBalance)
            }
        }
    }


    private fun rightSwipe() {
        if (available_balance >= swipeCost) rightAnimation()
        Timer("Update", true).schedule(300){
            runOnUiThread{
                val withUpdateBalance = true
                updateCardDetails(withUpdateBalance)
            }
        }

    }


    private fun rightAnimation() {
        val swipeAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1.5f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f
        )

        swipeAnimation.duration = 500
        binding.cardView.startAnimation(swipeAnimation)
    }


    private fun leftAnimation() {
        val swipeAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -1.5f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f
        )

        swipeAnimation.duration = 500
        binding.cardView.startAnimation(swipeAnimation)
    }


    private fun onDownSwipe() {
        val infoDialog = MoreInfoDialog(charities[charityIndex])
        infoDialog.show(supportFragmentManager, getString(R.string.MoreInfoDiag))
    }


    private fun sufficientFundCheck() : Boolean {
        var currMoneyAmt = available_balance
        return if (currMoneyAmt < swipeCost){
            fundAddDialog(getString(R.string.addFundsAdvanced))
            false
        } else{
            true
        }
    }


    private fun firstLoadCardDetails() {
        Glide.with(this).load(charities[charityIndex].imgUrl).into(
            binding.charityPic
        )
        binding.charityName.text = charities[charityIndex].title
        binding.charityType.text = charities[charityIndex].type
        binding.shortBioCharity.text = charities[charityIndex].shortIntro
    }


    private fun updateCardDetails(withUpdateBalance: Boolean){
        if (sufficientFundCheck()){
            Log.d("qwer", "qaz")
            if (withUpdateBalance) {
                changeUserBalance(available_balance - swipeCost)
                binding.tvBalance.text = getString(R.string.dollarSign) + "$available_balance"
            }

            charityIndex  = (charityIndex + 1) % charities.size
            firstLoadCardDetails()
        }
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }


    override fun onShowPress(p0: MotionEvent?) {
    }


    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }


    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }


    override fun onLongPress(p0: MotionEvent?) {
    }


    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
}