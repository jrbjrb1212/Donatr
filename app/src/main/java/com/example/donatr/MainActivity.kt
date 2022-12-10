package com.example.donatr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donatr.databinding.ActivityMainBinding
import android.widget.Toast;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var swipeCost = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fundDialog.setOnClickListener {
            val fundDialog = FundAddDiag()
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


}