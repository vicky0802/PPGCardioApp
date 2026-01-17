package com.example.ppgcardioapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.FragmentTrendsBinding

class TrendsFragment : Fragment() {
    private var _binding: FragmentTrendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTrendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hr7 = floatArrayOf(70f, 72f, 71f, 68f, 69f, 73f, 70f)
        val hrv7 = floatArrayOf(40f, 60f, 52f, 45f, 58f, 48f, 42f)
        val inst7 = floatArrayOf(12f, 10f, 8f, 9f, 14f, 11f, 10f)

        binding.chartHr.setColor(android.graphics.Color.parseColor("#FF5252"))
        binding.chartHr.updateData(hr7)
        binding.chartHr.setBaseline(70f)

        binding.chartHrv.setColor(android.graphics.Color.parseColor("#00C853"))
        binding.chartHrv.updateData(hrv7)
        binding.chartHrv.setBaseline(50f)

        binding.chartInstability.setColor(android.graphics.Color.parseColor("#00ACC1"))
        binding.chartInstability.updateData(inst7)
        binding.chartInstability.setBaseline(20f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = TrendsFragment()
    }
}
