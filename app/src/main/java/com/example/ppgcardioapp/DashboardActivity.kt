package com.example.ppgcardioapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.ActivityDashboardBinding
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.res.ColorStateList

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var role: String = "patient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootLayout) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, sys.top, v.paddingRight, sys.bottom)
            insets
        }

        val prefs = getSharedPreferences("ppg_prefs", Context.MODE_PRIVATE)
        role = prefs.getString("role", "patient") ?: "patient"
        updateRoleButton()
        setupBottomNav()
        showWelcomeIfFirstRun()
        if (role == "patient") loadFragment(HomeFragment.newInstance()) else loadFragment(AlertsFragment.newInstance())

        binding.btnSwitchRole.setOnClickListener {
            role = if (role == "patient") "clinician" else "patient"
            prefs.edit().putString("role", role).apply()
            updateRoleButton()
            setupBottomNav()
        }
    }

    private fun setupBottomNav() {
        val menu = binding.bottomNav.menu
        menu.clear()
        if (role == "patient") {
            menu.add(0, MENU_HOME, 0, "Home").setIcon(android.R.drawable.ic_menu_myplaces)
            menu.add(0, MENU_TRENDS, 1, "Trends").setIcon(android.R.drawable.ic_menu_sort_by_size)
            menu.add(0, MENU_REPORTS, 2, "Reports").setIcon(android.R.drawable.ic_menu_save)
            menu.add(0, MENU_PROFILE, 3, "Profile").setIcon(android.R.drawable.ic_menu_manage)
        } else {
            menu.add(0, MENU_DASHBOARD, 0, "Dashboard").setIcon(android.R.drawable.ic_menu_view)
            menu.add(0, MENU_ALERTS, 1, "Alerts").setIcon(android.R.drawable.ic_dialog_alert)
            menu.add(0, MENU_ANALYTICS, 2, "Analytics").setIcon(android.R.drawable.ic_menu_sort_by_size)
            menu.add(0, MENU_DOCS, 3, "Docs").setIcon(android.R.drawable.ic_menu_share)
        }
        binding.bottomNav.itemIconTintList = ColorStateList.valueOf(android.graphics.Color.BLACK)
        binding.bottomNav.itemTextColor = ColorStateList.valueOf(android.graphics.Color.BLACK)
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                MENU_HOME -> loadFragment(HomeFragment.newInstance())
                MENU_TRENDS -> loadFragment(TrendsFragment.newInstance())
                MENU_REPORTS -> loadFragment(ReportsFragment.newInstance())
                MENU_PROFILE -> loadFragment(ProfileFragment.newInstance())
                MENU_DASHBOARD -> loadFragment(ClinicianDashboardFragment.newInstance())
                MENU_ALERTS -> loadFragment(AlertsFragment.newInstance())
                MENU_ANALYTICS -> loadFragment(AnalyticsFragment.newInstance())
                MENU_DOCS -> loadFragment(DocsFragment.newInstance())
                else -> false
            }
        }
        binding.bottomNav.selectedItemId = if (role == "patient") MENU_HOME else MENU_ALERTS
    }

    private fun loadFragment(f: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, f)
            .commit()
        return true
    }

    private fun reloadCurrent() {
        val id = binding.bottomNav.selectedItemId
        binding.bottomNav.selectedItemId = id
    }

    private fun updateRoleButton() {
        val name = getSharedPreferences("ppg_prefs", Context.MODE_PRIVATE).getString("user_name", if (role == "patient") "Alex Kumar" else "Dr. Sarah Smith")
        binding.btnSwitchRole.text = if (role == "patient") "Switch to Clinician View" else "Switch to Patient View"
        binding.tvAppTitle.text = if (role == "patient") "CardioGrace" else "CardioGrace Clinician Portal"
    }

    private fun showWelcomeIfFirstRun() {
        val prefs = getSharedPreferences("ppg_prefs", Context.MODE_PRIVATE)
        val shown = prefs.getBoolean("welcome_shown", false)
        if (shown) return
        val view = layoutInflater.inflate(R.layout.dialog_welcome, null)
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()
        view.findViewById<android.widget.Button>(R.id.btnExplore).setOnClickListener {
            prefs.edit().putBoolean("welcome_shown", true).apply()
            dialog.dismiss()
        }
        dialog.show()
    }
    companion object {
        private const val MENU_HOME = 1001
        private const val MENU_TRENDS = 1002
        private const val MENU_REPORTS = 1003
        private const val MENU_PROFILE = 1004
        private const val MENU_DASHBOARD = 2001
        private const val MENU_ALERTS = 2002
        private const val MENU_ANALYTICS = 2003
        private const val MENU_DOCS = 2004
    }
}
