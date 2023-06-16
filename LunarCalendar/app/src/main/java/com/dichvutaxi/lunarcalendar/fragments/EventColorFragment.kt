package com.dichvutaxi.lunarcalendar.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.interfaces.IDialogEventListener
import com.dichvutaxi.lunarcalendar.interfaces.IOnColorSetListener

class EventColorFragment : DialogFragment(), View.OnClickListener {
    private var selectedColor = R.color.colorRed
    private var tempColor = R.color.colorRed
    private var mListener: IDialogEventListener? = null
    private var mColorSetListener: IOnColorSetListener? = null

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnRed -> tempColor = R.color.colorRed
            R.id.btnBlue -> tempColor = R.color.colorBlue
            R.id.btnBrown -> tempColor = R.color.colorBrown
            R.id.btnGreen -> tempColor = R.color.colorGreen
            R.id.btnLightBlue -> tempColor = R.color.colorLightBlue
            R.id.btnOrange -> tempColor = R.color.colorOrange
            R.id.btnPink -> tempColor = R.color.colorPink
            R.id.btnPurple -> tempColor = R.color.colorPurple
            R.id.btnYellow -> tempColor = R.color.colorYellow
        }
    }

    fun getSelectedColor(): Int {
        return selectedColor
    }

    fun setSelectedColor(value: Int) {
        selectedColor = value
    }

    fun setColorSetListener(listener: IOnColorSetListener?) {
        mColorSetListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.fragment_event_color, null)
        val btnRed = v.findViewById<Button>(R.id.btnRed)
        btnRed.setOnClickListener(this)
        val btnBlue = v.findViewById<Button>(R.id.btnBlue)
        btnBlue.setOnClickListener(this)
        val btnBrown = v.findViewById<Button>(R.id.btnBrown)
        btnBrown.setOnClickListener(this)
        val btnGreen = v.findViewById<Button>(R.id.btnGreen)
        btnGreen.setOnClickListener(this)
        val btnLightBlue = v.findViewById<Button>(R.id.btnLightBlue)
        btnLightBlue.setOnClickListener(this)
        val btnPink = v.findViewById<Button>(R.id.btnPink)
        btnPink.setOnClickListener(this)
        val btnPurple = v.findViewById<Button>(R.id.btnPurple)
        btnPurple.setOnClickListener(this)
        val btnOrange = v.findViewById<Button>(R.id.btnOrange)
        btnOrange.setOnClickListener(this)
        val btnYellow = v.findViewById<Button>(R.id.btnYellow)
        btnYellow.setOnClickListener(this)
        builder.setView(v)
            .setPositiveButton(R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                selectedColor = tempColor
                mColorSetListener!!.onColorSet(selectedColor)
                mListener!!.onDialogPositiveClick(this@EventColorFragment)
            }
            .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                mListener!!.onDialogNegativeClick(this@EventColorFragment)
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as IDialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement IDialogEventListener"
            )
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mListener = activity as IDialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement IDialogEventListener"
            )
        }
    }
}
