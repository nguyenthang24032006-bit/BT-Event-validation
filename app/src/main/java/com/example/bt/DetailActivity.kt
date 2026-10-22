package com.example.bt

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

/** Màn hình Chi tiết: hiển thị thông tin nhận từ màn hình Đăng kí. */
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_MSV = "extra_msv"
        const val EXTRA_CLASS = "extra_class"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView>(R.id.tvName).text = intent.getStringExtra(EXTRA_NAME)
        findViewById<TextView>(R.id.tvEmail).text = intent.getStringExtra(EXTRA_EMAIL)
        findViewById<TextView>(R.id.tvPhone).text = intent.getStringExtra(EXTRA_PHONE)
        findViewById<TextView>(R.id.tvMsv).text = intent.getStringExtra(EXTRA_MSV)
        findViewById<TextView>(R.id.tvClass).text = intent.getStringExtra(EXTRA_CLASS)

        // Button gọi intent chuyển về trang Đăng kí
        findViewById<MaterialButton>(R.id.btnBack).setOnClickListener {
            val i = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(i)
            finish()
        }
    }
}
