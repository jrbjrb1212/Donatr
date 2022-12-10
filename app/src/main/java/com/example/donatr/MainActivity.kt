package com.example.donatr

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.donatr.databinding.ActivityMainBinding
import android.widget.Toast;
import com.example.donatr.summary.SummaryActivity
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var binding: ActivityMainBinding
    private var swipeCost = 1
    var x2 : Float = 0.0f
    var x1 : Float = 0.0f
    var y2 : Float = 0.0f
    var y1 : Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fundDialog.setOnClickListener {
            val fundDialog = FundAddDiag()
        }

        binding.transHistory.setOnClickListener {
            val intent = Intent(applicationContext, SummaryActivity::class.java)
            startActivity(intent)
        }

        gestureDetector = GestureDetector(this,this)
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
                detectHorzontialSwipe(x1, x2, y1, y2)
            }
        }
    }


    private fun detectHorzontialSwipe(x1: Float, x2: Float, y1: Float, y2: Float) {
        val valueX : Float = x2-x1
        val valueY : Float = y2-y1

        // horizontal swipe
        if(kotlin.math.abs(valueX) > MIN_DISTANCE){
            // right swipe
            if (x2 > x1){
                Toast.makeText(this, "Right Swipe", Toast.LENGTH_SHORT).show()
            }
            // left swipe
            else{
                Toast.makeText(this, "Left Swipe", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun sufficientFundCheck() : Boolean {
        var currMoneyAmt = binding.entireCharityInfo.moneyAvailable.text.substring(2).toInt()!!
        return if (currMoneyAmt < swipeCost){
            insufficientFundsToast()
            false
        } else{
            true
        }
    }

    private fun insufficientFundsToast() {
        Toast.makeText(
            applicationContext,
            "Insufficient Funds in Account to Make Swipe",
            Toast.LENGTH_LONG).show();

    }

    private fun updateCardDetails(){
        // TODO: add firebase support to get the next charity information
        // TODO: charityObject is the data object for this
//        binding.entireCharityInfo.charityName = newCharityNameFromFireBase
//        binding.entireCharityInfo.charityPic = sameAbove
//        binding.entireCharityInfo.charityType = sameAbove
//        binding.entireCharityInfo.shortBioCharity = sameAbove

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