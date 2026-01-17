package com.example.ppgcardioapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ppgcardioapp.databinding.FragmentClinicianDashboardBinding

class ClinicianDashboardFragment : Fragment() {
    private var _binding: FragmentClinicianDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClinicianDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object { fun newInstance() = ClinicianDashboardFragment() }
}

