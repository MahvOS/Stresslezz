package com.mahvin.healthcare.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahvin.stresslezz.MainActivity
import com.mahvin.stresslezz.R
import com.mahvin.stresslezz.databinding.FragmentOnboardingFourthBinding


class OnboardingFourthFragment : Fragment() {
    private var _binding: FragmentOnboardingFourthBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingFourthBinding.inflate(inflater, container, false)
        sharedPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {
        fun newInstance() = OnboardingFourthFragment()
    }
}