package com.stasray.notesplus.auth

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.*
import com.stasray.notesplus.MainActivity
import com.stasray.notesplus.R
import com.stasray.notesplus.Utils

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
    }

    fun onRegisterClick(view : View) {
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val repeatPassowordEditText = findViewById<EditText>(R.id.editTextTextRepeatPassword)
        if (passwordEditText.text.toString() != repeatPassowordEditText.text.toString()) {
            repeatPassowordEditText.error = "Пароли не совпадают"
            repeatPassowordEditText.requestFocus()
            return
        }
        if (!Utils.isValidEmail(emailEditText.text)) {
            emailEditText.error = "Неверный адрес"
            emailEditText.requestFocus()
            return
        }
        if (passwordEditText.text.length !in 4..16) {
            passwordEditText.error = "Слишком короткий/длинный пароль (4-16)"
            passwordEditText.requestFocus()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                finishAffinity()
                startActivity(
                    Intent(this@RegisterActivity, MainActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                try {
                    task.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    emailEditText.error = "Проверьте правильность введенной почты"
                    emailEditText.requestFocus()
                } catch (e: FirebaseAuthUserCollisionException) {
                    emailEditText.error = "Почта уже используется"
                    emailEditText.requestFocus()
                } catch (e: Exception) {
                    Log.e(TAG, e.message!!)
                }
            }
        }
    }
}