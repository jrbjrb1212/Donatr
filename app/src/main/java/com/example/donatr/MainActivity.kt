package com.example.donatr

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.donatr.databinding.ActivityMainBinding
import android.widget.Toast;
import com.example.donatr.summary.MoreInfoDialog
import com.example.donatr.summary.SummaryActivity
import kotlinx.coroutines.runBlocking
import android.view.animation.Animation
import android.view.animation.TranslateAnimation


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var binding: ActivityMainBinding
    var x2 : Float = 0.0f
    var x1 : Float = 0.0f
    var y2 : Float = 0.0f
    var y1 : Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
        var available_balance: Double = 5.0
        var swipeCost = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO
        // testing remove later
        binding.tvBalance.text = binding.tvBalance.text.toString() + available_balance.toString()


        mainActivityBtnBindingsInit()

        gestureDetector = GestureDetector(this,this)
    }

    private fun mainActivityBtnBindingsInit() {
        binding.btnAddFunds.setOnClickListener {
            fundAddDialog("Please add funds below")
        }


        binding.btnMore.setOnClickListener{
            onDownSwipe()
        }


        binding.btnTransHist.setOnClickListener {
            val intent = Intent(applicationContext, SummaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fundAddDialog(contextType: String) {
        runBlocking {
            val fundDialog = FundAddDiag(contextType)
            fundDialog.show(supportFragmentManager, "Add Funds")
        }
    }

    fun updateShown(){
        binding.tvBalance.text = "$ $available_balance"
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

        // horizontal swipe
        if(kotlin.math.abs(valueX) > MIN_DISTANCE){
            // right swipe
            // TODO: Add right swipe functionality
            if (x2 > x1){
                if (sufficientFundCheck()) rightAnimation()
                val withUpdateBalance = true
                updateCardDetails(withUpdateBalance)
                //TODO remove before submitting final project
                Toast.makeText(this, "Right Swipe", Toast.LENGTH_SHORT).show()
            }
            // left swipe
            // TODO: Add left swipe functionality
            else{
                if (sufficientFundCheck()) leftAnimation()
                val withUpdateBalance = false
                updateCardDetails(withUpdateBalance)
                //TODO remove before submitting final project
                Toast.makeText(this, "Left Swipe", Toast.LENGTH_SHORT).show()
            }
        }
        if (kotlin.math.abs(valueY) > MIN_DISTANCE){
            if(y2 > y1){
                onDownSwipe()
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
        // TODO remove toast before submission
        Toast.makeText(this, "Down Swipe", Toast.LENGTH_LONG).show()
        val infoDialog = MoreInfoDialog()
        infoDialog.show(supportFragmentManager, "More Info Dialog")
    }


    private fun sufficientFundCheck() : Boolean {
        var currMoneyAmt = available_balance
        return if (currMoneyAmt < swipeCost){
            fundAddDialog("Insufficient funds detected. Please add additional funds to continue donating.")
            false
        } else{
            true
        }
    }


    private fun updateCardDetails(withUpdateBalance: Boolean){
        // TODO: add firebase support to get the next charity information
        // TODO: charityObject is the data object for this
        if (sufficientFundCheck()){
            if (withUpdateBalance) {
                available_balance -= swipeCost
                binding.tvBalance.text = "$ $available_balance"
            }
//        binding.entireCharityInfo.charityName = newCharityNameFromFireBase
//        binding.entireCharityInfo.charityPic = sameAbove
//        binding.entireCharityInfo.charityType = sameAbove
//        binding.entireCharityInfo.shortBioCharity = sameAbove
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