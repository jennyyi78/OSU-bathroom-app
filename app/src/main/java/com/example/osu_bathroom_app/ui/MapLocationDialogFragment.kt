package com.example.osu_bathroom_app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.osu_bathroom_app.R
import com.example.osu_bathroom_app.model.Bathroom

class MapLocationDialogFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var title : String? = arguments?.getString("title")
        var address : String? = arguments?.getString("address")
        var avgRating : String? = arguments?.getString("avgRating")
        val msg = String.format("%s\nAverage Rating: %s", address, avgRating)
        Log.i(TAG, "Arguments: " + arguments.toString())
        var dialog : Dialog =
                AlertDialog.Builder(requireContext())
                        .setTitle(title)
                        .setMessage(msg)
                        .setPositiveButton(getString(R.string.ok)) { _,_ -> }
                        .create()

        return dialog
    }

    companion object {
        const val TAG = "MapActivity"
    }
}