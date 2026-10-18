package com.example.baitap

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = intent.getStringExtra("USERNAME") ?: ""
        val mssv = intent.getStringExtra("MSSV") ?: ""

        findViewById<TextView>(R.id.tvUserName).text = "UserName: $userName"
        findViewById<TextView>(R.id.tvMssv).text = "MSSV: $mssv"

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish() // quay về Screen 1
        }
    }
}