package org.example.floodbusters.ui.consent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import org.example.floodbusters.PreInformationActivity

class ConsentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ConsentScreen(::goNext) }
    }

    private fun goNext() {
        startActivity(Intent(this, PreInformationActivity::class.java))
    }
}