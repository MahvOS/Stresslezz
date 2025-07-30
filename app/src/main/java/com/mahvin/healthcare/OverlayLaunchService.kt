package com.mahvin.stresslezz

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.*
import android.util.Log
import android.view.*
import androidx.core.app.NotificationCompat

class OverlayLaunchService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        Log.d("OverlayService", "Service created")
        setupForegroundService()
        showOverlay()
    }

    private fun setupForegroundService() {
        val channelId = "alarm_overlay_channel"


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alarm reminders"
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(this)
            }
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alarm Active")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .build()

        try {
            startForeground(1, notification)
        } catch (e: Exception) {
            Log.e("OverlayService", "Foreground start failed: ${e.message}")
        }
    }

    private fun showOverlay() {
        try {
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT
            )

            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)
            windowManager?.addView(overlayView, params)

            handler.postDelayed({
                removeOverlay()
                launchStepActivity()
                stopSelf()
            }, 1000)

        } catch (e: Exception) {
            Log.e("OverlayService", "Overlay failed: ${e.message}")
            stopSelf()
        }
    }

    private fun launchStepActivity() {
        try {
            startActivity(Intent(this, StepActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        } catch (e: Exception) {
            Log.e("OverlayService", "Activity launch failed: ${e.message}")
        }
    }

    private fun removeOverlay() {
        try {
            overlayView?.let { view ->
                windowManager?.removeView(view)
                overlayView = null
            }
        } catch (e: Exception) {
            Log.e("OverlayService", "Overlay removal failed: ${e.message}")
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        removeOverlay()
        super.onDestroy()
        Log.d("OverlayService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}