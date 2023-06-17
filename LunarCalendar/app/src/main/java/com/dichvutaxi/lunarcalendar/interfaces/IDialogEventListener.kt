package com.dichvutaxi.lunarcalendar.interfaces

import androidx.fragment.app.DialogFragment

interface IDialogEventListener {
    fun onDialogPositiveClick(dialog: DialogFragment)
    fun onDialogNegativeClick(dialog: DialogFragment)
}
