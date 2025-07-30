package com.mahvin.stresslezz

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.media.RingtoneManager
import android.os.*
import java.util.Calendar
import android.util.Log
import androidx.core.app.NotificationCompat

import com.mahvin.stresslezz.R

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val CHANNEL_ID = "step_channel"
        private const val NOTIF_ID = 1001

        private const val REQUEST_CODE_ALARM_PAGI = 100
        private const val REQUEST_CODE_ALARM_MALAM = 200
    }

    @SuppressLint("ScheduleExactAlarm")
    override fun onReceive(context: Context, intent: Intent?) {

        val selectedTime = intent?.getStringExtra("selected_time") ?: "PAGI"
        val selectedCopingMode = intent?.getStringExtra("selected_coping_mode") ?: "CEMAS"
        val triggeredRequestCode = intent?.getIntExtra("requestCode", 0) ?: 0

        Log.d("AlarmReceiver", "Alarm dipicu! Waktu: $selectedTime, Mode: $selectedCopingMode, Triggered Request Code: $triggeredRequestCode")

        val activityIntent = Intent(context, StepActivity::class.java).apply {
            putExtra("mode", selectedCopingMode)
            putExtra("time", selectedTime)
            putExtra("batchIndex", 0)
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
        }


        val overlayIntent = Intent(context, OverlayLaunchService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(overlayIntent)
        } else {
            context.startService(overlayIntent)
        }

        val fullScreenPending = PendingIntent.getActivity(
            context, triggeredRequestCode + 1000, activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("⏰ Waktunya Langkah!")
            .setContentText("Tap untuk memulai ${selectedCopingMode.lowercase()} ${selectedTime.lowercase()}") // Teks notif lebih spesifik
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(fullScreenPending, true)
            .setContentIntent(fullScreenPending)
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(NOTIF_ID, notif)

        try {
            context.startActivity(activityIntent)
            Log.d("AlarmReceiver", "StepActivity diluncurkan (Waktu: $selectedTime, Mode: $selectedCopingMode)")
        } catch (e: Exception) {
            Log.e("AlarmReceiver", "Gagal memulai StepActivity dari receiver: ${e.message}")
        }

        // === [AUTO SET ALARM BESOK] ===
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            when (selectedTime) {
                "PAGI" -> {
                    set(Calendar.HOUR_OF_DAY, 5)
                    set(Calendar.MINUTE, 0)
                    add(Calendar.DATE, 1)
                }
                "MALAM" -> {
                    set(Calendar.HOUR_OF_DAY, 20)
                    set(Calendar.MINUTE, 0)
                    if (get(Calendar.HOUR_OF_DAY) >= 20) {
                        add(Calendar.DATE, 1)
                    }
                }
            }
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val nextAlarmRequestCode = when (selectedTime) {
            "PAGI" -> REQUEST_CODE_ALARM_PAGI
            "MALAM" -> REQUEST_CODE_ALARM_MALAM
            else -> 0 // Fallback
        }

        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("selected_time", selectedTime) // Teruskan mode waktu yang sama
            putExtra("selected_coping_mode", selectedCopingMode) // Teruskan mode coping yang sama
            putExtra("requestCode", nextAlarmRequestCode) // Teruskan requestCode ke intent
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, nextAlarmRequestCode, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val showIntent = PendingIntent.getActivity(
            context, nextAlarmRequestCode + 1000, Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, showIntent),
            pendingIntent
        )

        Log.d("AlarmReceiver", "Alarm dijadwalkan ulang untuk besok (${calendar.time}) - Waktu: $selectedTime, Mode: $selectedCopingMode dengan requestCode: $nextAlarmRequestCode")
    }
}
