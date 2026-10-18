package com.example.baitap

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDbHelper(context: Context) :
    SQLiteOpenHelper(context, "students.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "student_id TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "avatar TEXT NOT NULL DEFAULT '')"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    private fun toValues(s: Student) = ContentValues().apply {
        put("name", s.name)
        put("student_id", s.studentId)
        put("email", s.email)
        put("avatar", s.avatar)
    }

    private fun readStudent(c: android.database.Cursor) = Student(
        id = c.getLong(0),
        name = c.getString(1),
        studentId = c.getString(2),
        email = c.getString(3),
        avatar = c.getString(4)
    )

    fun getAll(): List<Student> {
        val list = mutableListOf<Student>()
        readableDatabase.rawQuery(
            "SELECT id, name, student_id, email, avatar FROM students ORDER BY name COLLATE NOCASE",
            null
        ).use { c ->
            while (c.moveToNext()) list.add(readStudent(c))
        }
        return list
    }

    fun getById(id: Long): Student? {
        readableDatabase.rawQuery(
            "SELECT id, name, student_id, email, avatar FROM students WHERE id = ?",
            arrayOf(id.toString())
        ).use { c ->
            return if (c.moveToFirst()) readStudent(c) else null
        }
    }

    fun insert(s: Student): Long = writableDatabase.insert("students", null, toValues(s))

    fun update(s: Student): Int =
        writableDatabase.update("students", toValues(s), "id = ?", arrayOf(s.id.toString()))

    fun delete(id: Long): Int =
        writableDatabase.delete("students", "id = ?", arrayOf(id.toString()))
}