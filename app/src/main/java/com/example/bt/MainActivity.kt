package com.example.bt

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/** Màn hình Đăng kí: nhập thông tin + validation, hợp lệ thì chuyển qua DetailActivity. */
class MainActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilMsv: TextInputLayout
    private lateinit var tilClass: TextInputLayout

    private lateinit var edtName: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPhone: TextInputEditText
    private lateinit var edtMsv: TextInputEditText
    private lateinit var edtClass: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilMsv = findViewById(R.id.tilMsv)
        tilClass = findViewById(R.id.tilClass)
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPhone = findViewById(R.id.edtPhone)
        edtMsv = findViewById(R.id.edtMsv)
        edtClass = findViewById(R.id.edtClass)

        findViewById<MaterialButton>(R.id.btnRegister).setOnClickListener { onRegister() }
    }

    private fun onRegister() {
        val name = edtName.text.toString().trim().replace(Regex("\\s+"), " ")
        val email = edtEmail.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val msv = edtMsv.text.toString().trim()
        val className = edtClass.text.toString().trim()

        // Chạy đủ tất cả validation để hiện lỗi cùng lúc ở mọi ô
        val results = listOf(
            validateName(name),
            validateEmail(email),
            validatePhone(phone),
            validateMsv(msv),
            validateClass(className)
        )

        if (results.all { it }) {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_NAME, name)
                putExtra(DetailActivity.EXTRA_EMAIL, email)
                putExtra(DetailActivity.EXTRA_PHONE, phone)
                putExtra(DetailActivity.EXTRA_MSV, msv)
                putExtra(DetailActivity.EXTRA_CLASS, className)
            }
            startActivity(intent)
        }
    }

    private fun validateName(name: String): Boolean {
        val error = when {
            name.isEmpty() -> getString(R.string.err_name_empty)
            name.length < 2 -> getString(R.string.err_name_short)
            !Regex("^[\\p{L} ]+$").matches(name) -> getString(R.string.err_name_invalid)
            else -> null
        }
        tilName.error = error
        return error == null
    }

    private fun validateEmail(email: String): Boolean {
        val error = when {
            email.isEmpty() -> getString(R.string.err_email_empty)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.err_email_invalid)
            else -> null
        }
        tilEmail.error = error
        return error == null
    }

    private fun validatePhone(phone: String): Boolean {
        // SĐT Việt Nam: 0 hoặc +84, đầu số 3/5/7/8/9, tổng 10 số
        val error = when {
            phone.isEmpty() -> getString(R.string.err_phone_empty)
            !Regex("^(0|\\+84)(3|5|7|8|9)\\d{8}$").matches(phone) -> getString(R.string.err_phone_invalid)
            else -> null
        }
        tilPhone.error = error
        return error == null
    }

    private fun validateMsv(msv: String): Boolean {
        val error = when {
            msv.isEmpty() -> getString(R.string.err_msv_empty)
            !Regex("^\\d{13}$").matches(msv) -> getString(R.string.err_msv_invalid)
            else -> null
        }
        tilMsv.error = error
        return error == null
    }

    private fun validateClass(className: String): Boolean {
        val error = when {
            className.isEmpty() -> getString(R.string.err_class_empty)
            !Regex("^[A-Za-z0-9\\-]+$").matches(className) -> getString(R.string.err_class_invalid)
            else -> null
        }
        tilClass.error = error
        return error == null
    }
}
