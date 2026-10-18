package com.example.baitap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // TODO: thay theo đúng định dạng MSSV trong ảnh đề (đang là 8 chữ số tạm thời)
    private val mssvRegex = Regex("^\\d{8}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUserName = findViewById<EditText>(R.id.etUserName)
        val etMssv = findViewById<EditText>(R.id.etMssv)
        val btnClick = findViewById<Button>(R.id.btnClick)

        btnClick.setOnClickListener {
            val userName = etUserName.text.toString().trim()
            val mssv = etMssv.text.toString().trim()

            var valid = true

            if (userName.isEmpty()) {
                etUserName.error = "UserName không được để trống"
                valid = false
            }

            if (mssv.isEmpty()) {
                etMssv.error = "MSSV không được để trống"
                valid = false
            } else if (!mssvRegex.matches(mssv)) {
                etMssv.error = "MSSV sai định dạng"
                valid = false
            }

            if (valid) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("USERNAME", userName)
                intent.putExtra("MSSV", mssv)
                startActivity(intent)
            }
        }
    }
}