package com.stasray.notesplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import java.util.*

class NoteEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)
        val textEdit = findViewById<EditText>(R.id.editTextNote)
        var check = false
        var id : String? = null
        if (intent.extras != null) {
            if (intent.extras!!.get("text") != null) {
                textEdit.setText(intent.extras!!.get("text").toString())
            }
            if (intent.extras!!.get("id") != null) {
                id = intent.extras!!.get("id").toString()
            }
            if (intent.extras!!.get("check") != null) {
                check = intent.extras!!.get("check") as Boolean
            }
        }
        findViewById<ImageButton>(R.id.imageButtonBack).setOnClickListener {
            finish()
        }
        findViewById<ImageButton>(R.id.imageButtonSave).setOnClickListener {
            Utils.saveNote(id, NoteObject(textEdit.text.toString(), Date(), check)) {
                finish()
            }
        }
    }
}