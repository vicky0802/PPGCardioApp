package com.example.ppgcardioapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.FragmentReportsBinding

class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshMetrics()
    }

    private fun refreshMetrics() {
        val prefs = requireContext().getSharedPreferences("ppg_prefs", Context.MODE_PRIVATE)
        val hr = prefs.getFloat("latest_hr", -1f)
        val rmssd = prefs.getFloat("latest_rmssd", -1f)
        val instability = prefs.getFloat("latest_instability", -1f)
        val sqi = prefs.getFloat("latest_sqi", -1f)
        val triage = prefs.getInt("latest_triage", -1)
        val stamp = prefs.getLong("latest_timestamp", 0L)
        binding.tvHR.text = if (hr >= 0f) hr.toInt().toString() else "--"
        binding.tvHRV.text = if (rmssd >= 0f) String.format("%.0f", rmssd) else "--"
        binding.tvInstability.text = if (instability >= 0f) String.format("%.2f", instability) else "--"
        binding.tvSQI.text = if (sqi >= 0f) String.format("%.0f%%", 100f * sqi) else "--"
        binding.tvLastScan.text = if (stamp > 0L) java.text.SimpleDateFormat("MM/dd/yyyy, h:mm a").format(java.util.Date(stamp)) else "--"
        val label = when (triage) { 2 -> "RED"; 1 -> "AMBER"; 0 -> "GREEN"; else -> "--" }
        binding.tvAnalysis.text = when (triage) {
            2 -> "Abnormal vs baseline"
            1 -> "Some changes vs baseline"
            0 -> "Vitals stable"
            else -> "Run a scan to populate metrics"
        }
        binding.tvTriage.text = label
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ReportsFragment()
    }
}

