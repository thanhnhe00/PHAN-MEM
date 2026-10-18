package com.example.baitap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged

class EditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "student_db_id"
    }

    private lateinit var db: StudentDbHelper
    private var editing: Student? = null

    private lateinit var etName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAvatar: EditText
    private lateinit var imgAvatar: ImageView

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    // bỏ qua, vẫn hiển thị được trong phiên hiện tại
                }
                etAvatar.setText(uri.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = StudentDbHelper(this)

        etName = findViewById(R.id.etName)
        etStudentId = findViewById(R.id.etStudentId)
        etEmail = findViewById(R.id.etEmail)
        etAvatar = findViewById(R.id.etAvatar)
        imgAvatar = findViewById(R.id.imgAvatar)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)

        val id = intent.getLongExtra(EXTRA_ID, -1L)
        if (id != -1L) {
            editing = db.getById(id)
        }

        val current = editing
        if (current != null) {
            tvTitle.setText(R.string.edit_student)
            etName.setText(current.name)
            etStudentId.setText(current.studentId)
            etEmail.setText(current.email)
            etAvatar.setText(current.avatar)
        } else {
            tvTitle.setText(R.string.add_student)
        }

        loadAvatar(imgAvatar, etAvatar.text.toString())
        etAvatar.doAfterTextChanged { loadAvatar(imgAvatar, it.toString()) }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnPickImage).setOnClickListener {
            pickImage.launch(arrayOf("image/*"))
        }
        findViewById<Button>(R.id.btnSave).setOnClickListener { onSave() }
    }

    private fun onSave() {
        val name = etName.text.toString().trim()
        val studentId = etStudentId.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val avatar = etAvatar.text.toString().trim()

        var valid = true
        if (name.isEmpty()) {
            etName.error = getString(R.string.error_required)
            valid = false
        }
        if (studentId.isEmpty()) {
            etStudentId.error = getString(R.string.error_required)
            valid = false
        }
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.error_required)
            valid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.error_email)
            valid = false
        }
        if (!valid) return

        val current = editing
        if (current == null) {
            // Thêm mới
            db.insert(Student(name = name, studentId = studentId, email = email, avatar = avatar))
            Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Sửa: hỏi xác nhận trước
            AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_edit)
                .setPositiveButton(R.string.btn_yes) { _, _ ->
                    db.update(
                        Student(
                            id = current.id,
                            name = name,
                            studentId = studentId,
                            email = email,
                            avatar = avatar
                        )
                    )
                    Toast.makeText(this, R.string.toast_updated, Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton(R.string.btn_no, null)
                .show()
        }
    }
}