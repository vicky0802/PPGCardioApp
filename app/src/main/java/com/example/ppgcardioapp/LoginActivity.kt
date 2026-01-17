package com.example.ppgcardioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ppgcardioapp.databinding.ActivityLoginBinding
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.loginRoot) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, sys.top, v.paddingRight, sys.bottom)
            insets
        }

        binding.btnPatientDemo.setOnClickListener {
            binding.etEmail.setText("alex@demo.com")
            binding.etPassword.setText("1111")
            binding.btnLogin.performClick()
        }

        binding.btnClinicianDemo.setOnClickListener {
            binding.etEmail.setText("clinician@demo.com")
            binding.etPassword.setText("2222")
            binding.btnLogin.performClick()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text?.toString()?.trim() ?: ""
            val pwd = binding.etPassword.text?.toString() ?: ""

            val prefs = getSharedPreferences("ppg_prefs", MODE_PRIVATE)
            val isClinician = isClinicianCredential(email, pwd)
            val role = if (isClinician) "clinician" else "patient"
            val displayName = if (isClinician) "Dr. Sarah Smith" else "Alex Kumar"
            prefs.edit()
                .putString("role", role)
                .putString("user_name", displayName)
                .apply()

            val intent = Intent(this, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun isClinicianCredential(email: String, pwd: String): Boolean {
        if (email.equals("clinician@demo.com", ignoreCase = true) && pwd == "2222") return true
        if (email.startsWith("dr.", ignoreCase = true)) return true
        return false
    }
}
