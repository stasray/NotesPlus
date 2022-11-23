package com.stasray.notesplus

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity

class NoteAdapter(context: Context, resource: Int, noteList: List<Map.Entry<String, NoteObject>?>) :
    ArrayAdapter<Map.Entry<String, NoteObject>>(context, resource, noteList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        val note = getItem(position)
        if (view == null) {
            view = LayoutInflater
                .from(context)
                .inflate(R.layout.note_box, parent, false)
        }
        var text = note!!.value.text.toString()
        if (text.length > 100)
            text = text.substring(0, 100) + "..."
        view?.findViewById<TextView>(R.id.textViewBox)?.text = text
        val checkBox = view?.findViewById<CheckBox>(R.id.checkBox)
        checkBox?.isChecked = note.value.completed!!
        var note2 = note.value
        checkBox?.setOnCheckedChangeListener { compoundButton, b ->
            note2.completed = b
            Utils.saveNote(
                note.key,
                note2
            ) {}
        }
        view?.findViewById<ImageButton>(R.id.imageButtonRemove)?.setOnClickListener {
            remove(note)
            Utils.removeNote(note.key)
            notifyDataSetChanged()
        }
        view?.findViewById<CardView>(R.id.box)?.setOnClickListener {
            val i = Intent(context, NoteEditActivity::class.java)
            i.putExtra("id", note.key)
            i.putExtra("text", note.value.text)
            i.putExtra("check", note.value.completed)
            startActivity(
                context,
                i,
                ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle()
            )
        }

        return view!!
    }

}