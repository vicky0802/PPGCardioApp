package com.example.ppgcardioapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hr = floatArrayOf(94f, 93f, 96f, 95f, 94f, 95f, 96f, 97f, 98f, 99f, 100f, 91f, 92f, 95f)
        val hrv = floatArrayOf(50f, 52f, 80f, 70f, 42f, 58f, 49f, 65f, 48f, 72f, 46f, 58f, 68f, 60f)
        val resp = floatArrayOf(15f, 14f, 20f, 17f, 18f, 16f, 15f, 20f, 16f, 15f, 19f, 16f, 18f, 15f)
        val sqi = floatArrayOf(91f, 72f, 70f, 76f, 92f, 85f, 86f, 88f, 84f, 90f, 74f, 82f, 80f, 97f)

        binding.chartHr14.setColor(android.graphics.Color.parseColor("#FF5252"))
        binding.chartHr14.updateData(hr)
        binding.chartHr14.setBaseline(96f)

        binding.chartHrv14.setColor(android.graphics.Color.parseColor("#00C853"))
        binding.chartHrv14.updateData(hrv)
        binding.chartHrv14.setBaseline(50f)

        binding.chartResp14.setColor(android.graphics.Color.parseColor("#7E57C2"))
        binding.chartResp14.updateData(resp)
        binding.chartResp14.setBaseline(16f)

        binding.chartSqi14.setColor(android.graphics.Color.parseColor("#00ACC1"))
        binding.chartSqi14.updateData(sqi)
        binding.chartSqi14.setBaseline(85f)

        binding.btnAddNote.setOnClickListener {
            binding.etNote.setText("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object { fun newInstance() = AnalyticsFragment() }
}
