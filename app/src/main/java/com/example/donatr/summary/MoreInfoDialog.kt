package com.example.donatr.summary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.donatr.R
import com.example.donatr.databinding.MoreInfoDialogBinding

class MoreInfoDialog : DialogFragment() {

    private lateinit var infoViewBinding: MoreInfoDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val disciptBuilder = AlertDialog.Builder(requireContext())

        infoViewBinding = MoreInfoDialogBinding.inflate(
            requireActivity().layoutInflater
        )

        disciptBuilder.setView(infoViewBinding.root)
        disciptBuilder.setPositiveButton(getString(R.string.Done)) { dialog, which -> }

        return disciptBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            dialog.dismiss()
        }
    }

}