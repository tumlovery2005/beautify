package com.application.beautify.common

import android.content.Context
import android.support.v7.app.AlertDialog

fun showGeneralError(from: Context, title: String, message: String, button: String) {
  AlertDialog.Builder(from)
      .setTitle(title)
      .setMessage(message)
      .setPositiveButton(button) { dialog, _ -> dialog.dismiss() }
      .show()
}