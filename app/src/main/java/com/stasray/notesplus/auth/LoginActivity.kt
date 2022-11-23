package com.stasray.notesplus.auth

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.stasray.notesplus.R
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.stasray.notesplus.MainActivity
import com.stasray.notesplus.Utils


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
    }

    fun onLoginClick(view : View) {
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        if (!Utils.isValidEmail(emailEditText.text)) {
            emailEditText.error = "Неверный адрес"
            emailEditText.requestFocus()
            return
        }
        if (passwordEditText.text.length !in 4..16) {
            passwordEditText.error = "Слишком короткий/длинный пароль"
            passwordEditText.requestFocus()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                finishAffinity()
                startActivity(
                    Intent(this@LoginActivity, MainActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                Toast.makeText(this@LoginActivity, "Ошибка входа!", Toast.LENGTH_LONG).show()
            }
        }
    }

}