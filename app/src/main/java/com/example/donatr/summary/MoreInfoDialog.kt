package com.example.donatr.summary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.donatr.R
import com.example.donatr.data.Charity
import com.example.donatr.databinding.MoreInfoDialogBinding

class MoreInfoDialog (private val workingCharity : Charity) : DialogFragment() {

    private lateinit var infoViewBinding: MoreInfoDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val disciptBuilder = AlertDialog.Builder(requireContext())

        infoViewBinding = MoreInfoDialogBinding.inflate(
            requireActivity().layoutInflater
        )

        disciptBuilder.setView(infoViewBinding.root)
        updateView()
        disciptBuilder.setPositiveButton(getString(R.string.Done)) { dialog, which -> }

        return disciptBuilder.create()
    }


    private fun updateView() {
        Glide.with(requireContext()).load(workingCharity.imgUrl).into(
            infoViewBinding.imageView
        )
        infoViewBinding.tvCharityName.text = workingCharity.title
        infoViewBinding.tvType.text = workingCharity.type
        infoViewBinding.tvLocation.text = getString(R.string.NYC)
        infoViewBinding.tvDesc.text = workingCharity.longIntro
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