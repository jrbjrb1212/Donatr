package com.example.donatr

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.donatr.databinding.AddFundsDiagBinding
import com.example.donatr.databinding.MoreInfoDialogBinding

class FundAddDiag (private val contextType : String) : DialogFragment() {

    private lateinit var fundAddFundsDiagBinding: AddFundsDiagBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val disciptBuilder = AlertDialog.Builder(requireContext())

        fundAddFundsDiagBinding = AddFundsDiagBinding.inflate(
            requireActivity().layoutInflater
        )

        fundAddFundsDiagBinding.paymentContextMsg.text = contextType

        disciptBuilder.setView(fundAddFundsDiagBinding.root)
        disciptBuilder.setPositiveButton(getString(R.string.Done)) { dialog, which -> }

        return disciptBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (!checkView()){
                MainActivity.available_balance += fundAddFundsDiagBinding.moneyAddTxt.text.substring(2).toDouble()
                (context as MainActivity).updateShown()
                dialog.dismiss()
            }
        }
    }

    private fun checkView(): Boolean{
        fundAddFundsDiagBinding.paymentContextMsg
        try{
            if (fundAddFundsDiagBinding.moneyAddTxt.text.isEmpty() || fundAddFundsDiagBinding.moneyAddTxt.length() < 3) {
                fundAddFundsDiagBinding.moneyAddTxt.setText("$ ")
                fundAddFundsDiagBinding.moneyAddTxt.error = "Please enter a valid amount of money"
                return true
            } else if (fundAddFundsDiagBinding.moneyAddTxt.text.substring(0,2) != "$ " ||
                fundAddFundsDiagBinding.moneyAddTxt.text.substring(2).toDouble() < 0 ){
                fundAddFundsDiagBinding.moneyAddTxt.setText("$ ")
                fundAddFundsDiagBinding.moneyAddTxt.error = "Please enter a valid amount of money"
                return true
            }
        } catch (e : Exception){
            fundAddFundsDiagBinding.moneyAddTxt.setText("$ ")
            fundAddFundsDiagBinding.moneyAddTxt.error = "Please enter a valid amount of money"
        }

        return false
    }
}