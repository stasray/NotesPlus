package com.stasray.notesplus

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stasray.notesplus.auth.AuthSelectorActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    var noteList: ArrayList<Map.Entry<String, NoteObject>> = ArrayList()
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser == null) {
            finish()
            startActivity(
                Intent(this@MainActivity, AuthSelectorActivity::class.java))
            return
        }
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        loadNotes()
        refresher()

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            startActivity(
                Intent(this@MainActivity, NoteEditActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }

    private fun refresher() {
        Utils.currentUserDocRef
            .collection("notes")
            .addSnapshotListener { value, e ->
                loadNotes()
        }
    }

    private fun loadNotes() {
        Utils.loadNotes {
            noteList = ArrayList()
            val map = it
            for (entry in map) {
                noteList.add(java.util.AbstractMap.SimpleEntry(
                    entry.key,
                    entry.value
                ))
            }
            listView!!.adapter = NoteAdapter(this@MainActivity, 0, noteList)
        }
    }
}