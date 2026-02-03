package com.mktech.onovassignment.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object Utility {
    fun showErrorDialog(context: Context, title: String, message: String, reTry: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                reTry.invoke()
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .create()
            .show()
    }


    fun Exception.toUserMessage(): String = when (this) {
        is SocketTimeoutException -> "Request timed out"
        is IOException -> "No internet connection"
        is HttpException -> "Server error"
        else -> "Something went wrong"
    }
}