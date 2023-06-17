package com.dichvutaxi.lunarcalendar.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.interfaces.IDialogEventListener

class GoToLunarDateFragment : DialogFragment() {

    private var mListener: IDialogEventListener? = null
    private var editTextLunarDate: EditText? = null
    var lunarDateString: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.fragment_go_to_lunar_date, null)
        editTextLunarDate = v.findViewById(R.id.editTextLunarDate)
        builder.setView(v)
            .setTitle("Nhập ngày Âm lịch")
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    lunarDateString = editTextLunarDate?.text.toString()
                    mListener?.onDialogPositiveClick(this)
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    mListener?.onDialogNegativeClick(this)
                })
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as IDialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement IDialogEventListener")
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mListener = activity as IDialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement IDialogEventListener")
        }
    }
}
