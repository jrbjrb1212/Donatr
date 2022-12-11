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
                (context as MainActivity).changeUserBalance(MainActivity.available_balance
                        + fundAddFundsDiagBinding.moneyAddTxt.text.substring(2).toDouble())
                dialog.dismiss()
                (context as MainActivity).updateShown()
            }
        }
    }

    private fun resetView() {
        fundAddFundsDiagBinding.moneyAddTxt.setText(getString(R.string.dollarSign))
        fundAddFundsDiagBinding.moneyAddTxt.error = getString(R.string.validAmtPrompt)
    }

    private fun checkView(): Boolean{
        fundAddFundsDiagBinding.paymentContextMsg
        try{
            if (fundAddFundsDiagBinding.moneyAddTxt.text.isEmpty() || fundAddFundsDiagBinding.moneyAddTxt.length() < 3) {
                resetView()
                return true
            } else if (fundAddFundsDiagBinding.moneyAddTxt.text.substring(0,2) != "$ " ||
                fundAddFundsDiagBinding.moneyAddTxt.text.substring(2).toDouble() < 0 ){
                resetView()
                return true
            }
        } catch (e : Exception){
            resetView()
        }

        return false
    }
}