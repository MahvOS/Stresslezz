package com.mahvin.healthcare.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahvin.stresslezz.MainActivity
import com.mahvin.stresslezz.databinding.FragmentOnboardingFifthBinding

class OnboardingFifthFragment : Fragment() {
    private var _binding: FragmentOnboardingFifthBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingFifthBinding.inflate(inflater, container, false)
        sharedPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPermission.setOnClickListener {
            requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("onboarding_completed", true)
                .apply()

            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }


    }
    private fun markOnboardingCompleted() {
        sharedPrefs.edit().putBoolean("onboarding_completed", true).apply()
    }
    private fun navigateToMainApp() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}