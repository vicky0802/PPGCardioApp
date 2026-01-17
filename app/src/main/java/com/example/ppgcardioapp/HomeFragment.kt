package com.example.ppgcardioapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshTriage()
        binding.btnStartScan.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("auto_start", true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshTriage()
    }

    private fun refreshTriage() {
        val prefs = requireContext().getSharedPreferences("ppg_prefs", Context.MODE_PRIVATE)
        val triage = prefs.getInt("latest_triage", -1)
        val label = when (triage) { 2 -> "RED"; 1 -> "AMBER"; 0 -> "GREEN"; else -> "--" }
        binding.tvTriage.text = label
        val color = when (triage) { 2 -> Color.parseColor("#D50000"); 1 -> Color.parseColor("#FFAB00"); 0 -> Color.parseColor("#00C853"); else -> Color.parseColor("#424242") }
        binding.triageCircle.setBackgroundColor(color)
        val msg = when (triage) {
            2 -> "Urgent attention recommended"
            1 -> "Needs attention"
            0 -> "Stable"
            else -> "Run a PPG scan"
        }
        binding.tvMessage.text = msg
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
