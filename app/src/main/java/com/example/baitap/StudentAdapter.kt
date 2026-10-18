package com.example.baitap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Hiển thị avatar từ link ảnh hoặc ảnh chọn từ thiết bị
fun loadAvatar(img: ImageView, source: String) {
    if (source.isBlank()) {
        img.setImageResource(R.drawable.ic_avatar_placeholder)
        return
    }
    Glide.with(img)
        .load(source.trim())
        .circleCrop()
        .placeholder(R.drawable.ic_avatar_placeholder)
        .error(R.drawable.ic_avatar_placeholder)
        .into(img)
}

class StudentAdapter(
    private var items: List<Student>,
    private val onClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgAvatar)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvStudentId: TextView = v.findViewById(R.id.tvStudentId)
        val tvEmail: TextView = v.findViewById(R.id.tvEmail)
    }

    fun submit(newItems: List<Student>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position]
        holder.tvName.text = s.name
        holder.tvStudentId.text = s.studentId
        holder.tvEmail.text = s.email
        loadAvatar(holder.img, s.avatar)
        holder.itemView.setOnClickListener { onClick(s) }
    }

    override fun getItemCount() = items.size
}