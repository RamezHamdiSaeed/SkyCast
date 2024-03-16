package com.example.skycast.utility

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

object NoInternetDialogFragment : DialogFragment() {
    var isTriggered:Boolean=false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("No internet connection. Please check your connection and try again.")
            .setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                isTriggered=false

            }
            .create()
    }
}
