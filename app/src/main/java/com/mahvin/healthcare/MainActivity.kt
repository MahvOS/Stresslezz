package com.mahvin.stresslezz

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mahvin.stresslezz.AlarmReceiver
import com.mahvin.stresslezz.StepActivity
import com.mahvin.stresslezz.R
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedTime: String? = null
    private var selectedCopingMode: String? = null
    private lateinit var tvSelected: TextView
    private lateinit var btnPagi: CardView
    private lateinit var btnMalam: CardView
    private lateinit var btnAtasiCemas: CardView
    private lateinit var btnAtasiBurnout: CardView
    private lateinit var btnStart: Button
    private lateinit var toggleAlarm: SwitchCompat

    private val PREFS_NAME = "app_prefs"
    private val ALARM_SET_KEY = "alarm_set"
    private val OVERLAY_DIALOG_SHOWN_KEY = "overlay_dialog_shown"
    private val BATTERY_OPTIMIZATION_DIALOG_SHOWN_KEY = "battery_optimization_dialog_shown"
    private val ALARM_ENABLED_KEY = "alarm_enabled"
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 101
    private val OVERLAY_PERMISSION_REQUEST_CODE = 1001
    private val REQUEST_CODE_ALARM_PAGI = 100
    private val REQUEST_CODE_ALARM_MALAM = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashOnScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false
        }, 4000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            )
        }

        initViews()
        loadSelections()
        updateCardColors()
        setupClickListeners()
        setupNotificationChannel()
        checkAndRequestAllPermissions()
        setupAlarmToggle()
        setupStartButton()

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (!prefs.getBoolean(ALARM_SET_KEY, false)) {
            setDailyAlarm()
            prefs.edit().putBoolean(ALARM_SET_KEY, true).apply()
        }
    }

    private fun initViews() {
        btnPagi = findViewById(R.id.btnPagi)
        btnMalam = findViewById(R.id.btnMalam)
        btnAtasiCemas = findViewById(R.id.btnAtasiCemas)
        btnAtasiBurnout = findViewById(R.id.btnAtasiBurnout)
        tvSelected = findViewById(R.id.tvSelectedMode)
        btnStart = findViewById(R.id.btnStartNow)
        toggleAlarm = findViewById(R.id.toggleAlarm)
    }

    private fun setupClickListeners() {
        btnPagi.setOnClickListener {
            saveTimeSelection("PAGI", 5, 0)
            updateSelectedDisplay()
            updateCardColors()
        }

        btnMalam.setOnClickListener {
            saveTimeSelection("MALAM", 20, 0)
            updateSelectedDisplay()
            updateCardColors()
        }

        btnAtasiCemas.setOnClickListener {
            saveCopingModeSelection("CEMAS")
            updateSelectedDisplay()
            updateCardColors()
        }

        btnAtasiBurnout.setOnClickListener {
            saveCopingModeSelection("BURNOUT")
            updateSelectedDisplay()
            updateCardColors()
        }
    }

    private fun setupAlarmToggle() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isAlarmEnabled = prefs.getBoolean(ALARM_ENABLED_KEY, true)
        toggleAlarm.isChecked = isAlarmEnabled
        updateAlarmStatusUI(isAlarmEnabled)

        toggleAlarm.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(ALARM_ENABLED_KEY, isChecked).apply()
            updateAlarmStatusUI(isChecked)

            if (isChecked) {
                enableAlarm()
                Toast.makeText(this, "Alarm diaktifkan", Toast.LENGTH_SHORT).show()
            } else {
                disableAlarm()
                Toast.makeText(this, "Alarm dimatikan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableAlarm() {
        when (selectedTime) {
            "PAGI" -> scheduleAlarmAt(5, 0)
            "MALAM" -> scheduleAlarmAt(20, 0)
            else -> scheduleAlarmAt(5, 0)
        }
    }

    private fun disableAlarm() {
        cancelExistingAlarms()
    }

    private fun updateAlarmStatusUI(isEnabled: Boolean) {
        if (isEnabled) {
            toggleAlarm.text = "Alarm AKTIF"
            toggleAlarm.thumbTintList = ContextCompat.getColorStateList(this, R.color.green)
        } else {
            toggleAlarm.text = "Alarm NONAKTIF"
            toggleAlarm.thumbTintList = ContextCompat.getColorStateList(this, R.color.red)
        }

        val alpha = if (isEnabled) 1.0f else 0.5f
        btnPagi.alpha = alpha
        btnMalam.alpha = alpha
        btnAtasiCemas.alpha = alpha
        btnAtasiBurnout.alpha = alpha
    }

    private fun loadSelections() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        selectedTime = prefs.getString("selected_time", null)
        selectedCopingMode = prefs.getString("selected_coping_mode", null)
        updateSelectedDisplay()
    }

    private fun updateSelectedDisplay() {
        val timeDisplay = when (selectedTime) {
            "PAGI" -> "05:00"
            "MALAM" -> "20:00"
            else -> "-"
        }
        val copingModeDisplay = selectedCopingMode ?: "-"
        tvSelected.text = "Waktu & Mode Terpilih: $timeDisplay ($copingModeDisplay)"
    }

    private fun updateCardColors() {
        btnPagi.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_pagi_default))
        btnMalam.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_malam_default))
        btnAtasiBurnout.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_mode_default))
        btnAtasiCemas.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_mode_default))

        when (selectedTime) {
            "PAGI" -> btnPagi.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_pagi_selected))
            "MALAM" -> btnMalam.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_malam_selected))
        }

        when (selectedCopingMode) {
            "BURNOUT" -> btnAtasiBurnout.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_mode_selected))
            "CEMAS" -> btnAtasiCemas.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_mode_selected))
        }
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "step_channel",
                "Step Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel untuk alarm langkah"
                enableVibration(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun checkAndRequestAllPermissions() {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !pm.isIgnoringBatteryOptimizations(packageName)) {
            if (!prefs.getBoolean(BATTERY_OPTIMIZATION_DIALOG_SHOWN_KEY, false)) {
                showBatteryOptimizationDialog()
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            if (!prefs.getBoolean(OVERLAY_DIALOG_SHOWN_KEY, false)) {
                showOverlayPermissionDialog()
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            if (!notifManager.canUseFullScreenIntent()) {
                showFullScreenIntentDialog()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog()
            }
        }
    }

    private fun showBatteryOptimizationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Matikan Penghemat Baterai")
            .setMessage("Agar alarm bisa jalan walau layar mati, matikan penghemat baterai untuk app ini.")
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                    .putBoolean(BATTERY_OPTIMIZATION_DIALOG_SHOWN_KEY, true).apply()
            }
            .setNegativeButton("Sudah") { dialog, _ ->
                dialog.dismiss()
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                    .putBoolean(BATTERY_OPTIMIZATION_DIALOG_SHOWN_KEY, true).apply()
            }
            .show()
    }

    private fun showOverlayPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Izin Tampil di Atas Aplikasi Lain")
            .setMessage("Aplikasi butuh izin ini untuk menampilkan informasi penting di layar kunci.")
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                    .putBoolean(OVERLAY_DIALOG_SHOWN_KEY, true).apply()
            }
            .setNegativeButton("Sudah") { dialog, _ ->
                dialog.dismiss()
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                    .putBoolean(OVERLAY_DIALOG_SHOWN_KEY, true).apply()
            }
            .show()
    }

    private fun showFullScreenIntentDialog() {
        AlertDialog.Builder(this)
            .setTitle("Izin Tampilan Layar Penuh")
            .setMessage("Agar alarm dapat muncul saat terkunci, aktifkan izin ini.")
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                val intent = Intent(Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
            .setNegativeButton("Nanti") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showExactAlarmPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Izin Alarm Akurat")
            .setMessage("Agar alarm berbunyi tepat waktu, aktifkan izin ini.")
            .setPositiveButton("Buka Pengaturan") { _, _ ->
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
            .setNegativeButton("Nanti") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Izin Overlay Diberikan!", Toast.LENGTH_SHORT).show()
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                    .putBoolean(OVERLAY_DIALOG_SHOWN_KEY, false).apply()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            NOTIFICATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Izin Notifikasi Diberikan!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveTimeSelection(timeMode: String, hour: Int, minute: Int) {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (!prefs.getBoolean(ALARM_ENABLED_KEY, true)) {
            Toast.makeText(this, "Alarm tidak aktif - pilih waktu tapi alarm tidak akan berbunyi", Toast.LENGTH_SHORT).show()
        }

        prefs.edit().putString("selected_time", timeMode).apply()
        selectedTime = timeMode
        cancelExistingAlarms()
        scheduleAlarmAt(hour, minute)
        Toast.makeText(this, "Alarm waktu $timeMode diset jam $hour:$minute", Toast.LENGTH_SHORT).show()
    }

    private fun saveCopingModeSelection(copingMode: String) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
            .putString("selected_coping_mode", copingMode).apply()
        selectedCopingMode = copingMode
        Toast.makeText(this, "Mode: $copingMode dipilih", Toast.LENGTH_SHORT).show()
    }

    private fun setupStartButton() {
        btnStart.setOnClickListener {
            if (selectedTime == null || selectedCopingMode == null) {
                Toast.makeText(this, "Pilih Waktu dan Mode terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, StepActivity::class.java).apply {
                putExtra("mode", selectedCopingMode)
                putExtra("time", selectedTime)
                putExtra("batchIndex", 0)
            })
        }
    }

    private fun cancelExistingAlarms() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pagiIntent = Intent(this, AlarmReceiver::class.java)
        val pagiPendingIntent = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE_ALARM_PAGI,
            pagiIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pagiPendingIntent?.let {
            alarmManager.cancel(it)
            Log.d("AlarmScheduler", "Alarm PAGI dibatalkan.")
        }

        val malamIntent = Intent(this, AlarmReceiver::class.java)
        val malamPendingIntent = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE_ALARM_MALAM,
            malamIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        malamPendingIntent?.let {
            alarmManager.cancel(it)
            Log.d("AlarmScheduler", "Alarm MALAM dibatalkan.")
        }
    }

    private fun setDailyAlarm() {
        if (selectedTime == null) {
            selectedTime = "PAGI"
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                .putString("selected_time", "PAGI").apply()
        }
        val hour = if (selectedTime == "PAGI") 5 else 20
        cancelExistingAlarms()
        scheduleAlarmAt(hour, 0)
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
            .putBoolean(ALARM_SET_KEY, true).apply()
    }

    private fun scheduleAlarmAt(hour: Int, minute: Int) {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (!prefs.getBoolean(ALARM_ENABLED_KEY, true)) {
            Log.d("AlarmScheduler", "Alarm tidak dijadwalkan karena status nonaktif")
            return
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("selected_time", selectedTime)
            putExtra("selected_coping_mode", selectedCopingMode)
        }

        val requestCode = when (selectedTime) {
            "PAGI" -> REQUEST_CODE_ALARM_PAGI
            "MALAM" -> REQUEST_CODE_ALARM_MALAM
            else -> 0
        }
        intent.putExtra("requestCode", requestCode)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Izin alarm akurat diperlukan", Toast.LENGTH_LONG).show()
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                return
            }
        }

        val showIntent = PendingIntent.getActivity(
            this,
            requestCode + 1000,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, showIntent),
            pendingIntent
        )

        Log.d("AlarmScheduler", "Alarm diset jam $hour:$minute untuk $selectedTime")
    }

    override fun onResume() {
        super.onResume()
        loadSelections()
        updateCardColors()

        val isEnabled = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .getBoolean(ALARM_ENABLED_KEY, true)
        updateAlarmStatusUI(isEnabled)

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !pm.isIgnoringBatteryOptimizations(packageName)) {
            Toast.makeText(this, "PERHATIAN: Penghemat baterai aktif. Alarm mungkin tidak akurat!", Toast.LENGTH_LONG).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "PERHATIAN: Izin overlay diperlukan untuk tampilan di layar kunci.", Toast.LENGTH_LONG).show()
        }

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && !notifManager.canUseFullScreenIntent()) {
            Toast.makeText(this, "PERHATIAN: Izin Tampilan Layar Penuh tidak aktif.", Toast.LENGTH_LONG).show()
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            Toast.makeText(this, "PERHATIAN: Izin alarm akurat tidak diberikan.", Toast.LENGTH_LONG).show()
        }
    }
}