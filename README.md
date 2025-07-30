iya bang, ini open source

## Installation Guide.. or whtever lmao
1. **Clone Repository**:
   ```bash
   git clone https://github.com/username/Stresslezz.git
2. **Buka di Android Studio**:
   File → Open → Pilih folder Stresslezz (atau folder yg lu buat)
   Tunggu Gradle sync selesai (lihat progress bar bawah)
3. **Coba liat aplikasinya**:
   Test make emulator dari android studionya atau apapun yang menting jalan.

## Cara run di android gmn? 🤔
1. **Build APK**:
   Build → Generate Signed Bundle / APK
   Pilih APK → Isi keystore (atau buat baru)
   hasilnya biasanya ada di app/build/outputs/apk/debug/app-debug.apk
   
### **Catatan Khusus**  
- **Jangan commit** `local.properties` (file auto-generate)  
- Kalo ada error **"Manifest merger failed"**, cek `AndroidManifest.xml` di semua modul  

---

**📌 Contoh Tampilan di Android Studio:**  

![Home Screen](https://imgur.com/a/XbcQxF8)

