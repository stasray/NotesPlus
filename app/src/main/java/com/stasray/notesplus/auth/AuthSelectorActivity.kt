package com.stasray.notesplus.auth

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stasray.notesplus.R

class AuthSelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_selector)
    }

    fun onLoginClick(view: View) {
        startActivity(
            Intent(this@AuthSelectorActivity, LoginActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
    fun onRegisterClick(view: View) {
        startActivity(
            Intent(this@AuthSelectorActivity, RegisterActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}