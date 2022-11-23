package com.stasray.notesplus

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


object Utils {

    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef : DocumentReference
        get() = firestoreInstance.document("users/${
            FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID is null.")}")

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun loadNotes(onCallback: (LinkedHashMap<String, NoteObject>) -> (Unit)) {
        currentUserDocRef.collection("notes")
            .orderBy("completed", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                val map = LinkedHashMap<String, NoteObject>()
                for (doc in it.documents) {
                    map[doc.id] = doc.toObject(NoteObject::class.java)!!
                }
                onCallback(map)
            }
    }

    fun saveNote(id : String?, note : NoteObject, onCallback: (String) -> (Unit)) {
        getDocumentId(id) { newId ->
            currentUserDocRef.collection("notes")
                .document(newId)
                .set(note)
                .addOnSuccessListener {
                    onCallback(newId)
                }
        }
    }

    private fun getDocumentId(s : String?, onCallback: (String) -> Unit) {
        if (s != null) onCallback(s)
        else {
            currentUserDocRef.collection("notes")
                .document().get().addOnSuccessListener {
                    onCallback(it.id)
                }
        }
    }

    fun removeNote(id : String) {
        currentUserDocRef.collection("notes")
            .document(id)
            .delete()
    }

}