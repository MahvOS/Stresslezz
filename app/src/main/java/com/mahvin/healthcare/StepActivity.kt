package com.mahvin.stresslezz

import android.graphics.Color
import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.mahvin.healthcare.StepData
import com.mahvin.stresslezz.databinding.ActivityStepBinding
import kotlin.random.Random

class StepActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStepBinding
    private var mediaPlayer: MediaPlayer? = null
    private var selectedTime: String = "PAGI"
    private var selectedCopingMode: String = "CEMAS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowFlags()

        initMediaPlayer()

        selectedTime = intent.getStringExtra("time")?.uppercase() ?: "PAGI"
        selectedCopingMode = intent.getStringExtra("mode")?.uppercase() ?: "CEMAS"

        applyTimeBasedColors(selectedTime)

        loadAndDisplaySteps()

        setupClickListeners()

        setupBackPressHandler()
    }

    private fun setupWindowFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.chillersong).apply {
                this?.isLooping = true
                this?.start()
            }
        } catch (e: Exception) {
            Log.e("StepActivity", "MediaPlayer error: ${e.message}")
            mediaPlayer = null
        }
    }

    private fun applyTimeBasedColors(time: String) {
        val (cardColor, btnColor, iconColor, bgColor) = when (time) {
            "PAGI" -> Quad("#0FA968", "#0FA968", "#0FA968", "#E3F1EE")
            "MALAM" -> Quad("#7D36E3", "#7D36E3", "#7D36E3", "#4A2578")
            else -> Quad("#00C97F", "#00DF81", "#000000", "#E3F1EE")
        }

        binding.cardContent.setCardBackgroundColor(Color.parseColor(cardColor))
        binding.btnDone.backgroundTintList = ColorStateList.valueOf(Color.parseColor(btnColor))
        binding.ivBellIcon.imageTintList = ColorStateList.valueOf(Color.parseColor(iconColor))
        binding.root.setBackgroundColor(Color.parseColor(bgColor))
    }

    private fun loadAndDisplaySteps() {
        val allStepsForCombination = when (selectedCopingMode) {
            "CEMAS" -> when (selectedTime) {
                "PAGI" -> StepData.stepsCemasPagi
                "MALAM" -> StepData.stepsCemasMalam
                else -> emptyList()
            }
            "BURNOUT" -> when (selectedTime) {
                "PAGI" -> StepData.stepsBurnoutPagi
                "MALAM" -> StepData.stepsBurnoutMalam
                else -> emptyList()
            }
            else -> {
                Toast.makeText(this, "Mode tidak dikenal: $selectedCopingMode", Toast.LENGTH_SHORT).show()
                emptyList()
            }
        }

        if (allStepsForCombination.isEmpty()) {
            Toast.makeText(this,
                "Tidak ada langkah ditemukan untuk kombinasi: Waktu = $selectedTime, Mode = $selectedCopingMode",
                Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val randomStep = allStepsForCombination.randomOrNull() ?: run {
            Toast.makeText(this, "Gagal memilih langkah acak", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.tvArabicText.text = randomStep.arab
        binding.tvTranslation.text = "${randomStep.ayat}\n\n${randomStep.arti}\n\nTafsir:\n${randomStep.tafsir}"
        binding.tvGroundingInstruction.text = randomStep.aksi
    }

    private fun setupClickListeners() {
        binding.ivBellIcon.setOnClickListener {
            finish()
        }

        binding.btnDone.setOnClickListener {
            Toast.makeText(this, "Langkah telah selesai!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@StepActivity,
                    "Selesaikan langkah terlebih dahulu",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    private data class Quad<T>(val first: T, val second: T, val third: T, val fourth: T)
}