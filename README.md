# Stresslezz

> Build better habits, reduce stress, and stay consistent with scheduled wellness reminders.

## 📖 Overview

Stresslezz is an Android application designed to help users maintain healthy daily routines through scheduled reminders and activity prompts.

The application focuses on reliability, ensuring alarms continue to work even when the device is locked, the application is closed, or battery optimization mechanisms attempt to restrict background execution.

## ✨ Features

* Scheduled daily reminders
* Morning routine alarm (05:00)
* Night routine alarm (22:00)
* High-priority notifications
* Lock screen support
* Exact alarm scheduling
* Battery optimization handling
* Overlay permission support
* Guided onboarding process
* Background execution support

## 📸 Application Flow

1. User opens Stresslezz.
2. Onboarding guides users through required permissions.
3. User selects preferred reminder mode.
4. Alarm is scheduled using Android AlarmManager.
5. When the scheduled time arrives:

   * Alarm is triggered.
   * Notification is displayed.
   * Activity screen is launched.
6. User completes the scheduled activity.

## 🏗️ Architecture

```text
User
 │
 ▼
MainActivity
 │
 ▼
AlarmManager
 │
 ▼
BroadcastReceiver
 │
 ▼
Notification Service
 │
 ▼
StepActivity
```

## 🛠️ Tech Stack

### Mobile

* Java
* Android SDK
* Material Design Components

### Android Components

* AlarmManager
* BroadcastReceiver
* WakeLock
* NotificationManager
* SharedPreferences
* Exact Alarm API

### Permissions

* POST_NOTIFICATIONS
* SCHEDULE_EXACT_ALARM
* WAKE_LOCK
* SYSTEM_ALERT_WINDOW (Overlay)

## 📂 Project Structure

```text
app/
├── activities/
│   ├── MainActivity
│   ├── StepActivity
│   └── OnboardingActivity
│
├── receivers/
│   └── AlarmReceiver
│
├── services/
│   └── AlarmService
│
├── utils/
│   ├── AlarmHelper
│   ├── PermissionHelper
│   └── NotificationHelper
│
└── res/
    ├── layout/
    ├── drawable/
    └── values/
```

## ⚙️ Installation

### Clone Repository

```bash
git clone https://github.com/yourusername/stresslezz.git
```

### Open Project

```bash
Android Studio Hedgehog or newer
```

### Build

```bash
Build > Make Project
```

### Run

```bash
Run on Android 10+
```

## 📋 Requirements

* Android 10 (API 29) or higher
* Notification Permission
* Exact Alarm Permission
* Overlay Permission (optional)
* Auto Start enabled on certain manufacturers (OPPO, Vivo, Xiaomi, Realme)

## 🔒 Known Challenges

Some Android manufacturers apply aggressive battery optimization policies that may prevent alarms from launching activities automatically.

Manufacturers commonly affected:

* OPPO
* Vivo
* Xiaomi
* Realme

Stresslezz provides onboarding guidance to help users configure these settings correctly.

## 🚀 Roadmap

### Version 1.0

* [x] Morning alarm
* [x] Night alarm
* [x] Exact alarm support
* [x] Permission onboarding

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

Feel free to fork the repository and submit a pull request.
