package com.mahvin.stresslezz.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mahvin.stresslezz.MainActivity
import com.mahvin.healthcare.OnboardingPagerAdapter
import com.mahvin.stresslezz.R
import androidx.fragment.app.Fragment
import com.mahvin.healthcare.onboarding.OnboardingFifthFragment
import com.mahvin.healthcare.onboarding.OnboardingFirstFragment
import com.mahvin.healthcare.onboarding.OnboardingFourthFragment
import com.mahvin.healthcare.onboarding.OnboardingSecondFragment
import com.mahvin.healthcare.onboarding.OnboardingThirdFragment
import com.mahvin.stresslezz.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var fragments: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HealthCare)
        super.onCreate(savedInstanceState)

        if (getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getBoolean("onboarding_shown", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.post {
            setupViewPager()
        }
    }

    private fun setupViewPager() {
        fragments = listOf(
            OnboardingFirstFragment(),
            OnboardingSecondFragment(),
            OnboardingThirdFragment(),
            OnboardingFourthFragment(),
            OnboardingFifthFragment()
        )

        binding.viewPager.adapter = OnboardingPagerAdapter(this, fragments)
        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { _, _ -> }.attach()

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < fragments.size - 1) {
                binding.viewPager.currentItem += 1
            } else {
                markOnboardingComplete()
            }
        }

        binding.btnSkip.setOnClickListener {
            markOnboardingComplete()
        }


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == fragments.size - 1) {
                    binding.btnNext.visibility = View.GONE
                    binding.btnSkip.visibility = View.GONE
                } else {
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun markOnboardingComplete() {
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
            .putBoolean("onboarding_shown", true)
            .apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}