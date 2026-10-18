package com.example.baitap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: StudentDbHelper
    private lateinit var adapter: StudentAdapter
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = StudentDbHelper(this)
        tvEmpty = findViewById(R.id.tvEmpty)

        adapter = StudentAdapter(emptyList()) { student -> showDetailDialog(student) }
        val rv = findViewById<RecyclerView>(R.id.rvStudents)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val list = db.getAll()
        adapter.submit(list)
        tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    // Trang 2: hộp thoại xem chi tiết
    private fun showDetailDialog(student: Student) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_student_detail, null)
        loadAvatar(view.findViewById<ImageView>(R.id.imgAvatar), student.avatar)
        view.findViewById<TextView>(R.id.tvName).text = student.name
        view.findViewById<TextView>(R.id.tvStudentId).text =
            getString(R.string.label_student_id) + ": " + student.studentId
        view.findViewById<TextView>(R.id.tvEmail).text =
            getString(R.string.label_email) + ": " + student.email

        AlertDialog.Builder(this)
            .setTitle(R.string.student_detail)
            .setView(view)
            .setPositiveButton(R.string.btn_edit) { _, _ ->
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra(EditActivity.EXTRA_ID, student.id)
                startActivity(intent)
            }
            .setNegativeButton(R.string.btn_delete) { _, _ -> confirmDelete(student) }
            .setNeutralButton(R.string.btn_close, null)
            .show()
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_title)
            .setMessage(R.string.confirm_delete)
            .setPositiveButton(R.string.btn_yes) { _, _ ->
                db.delete(student.id)
                Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show()
                loadData()
            }
            .setNegativeButton(R.string.btn_no, null)
            .show()
    }
}