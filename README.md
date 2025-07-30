iya bang, ini open source

## Installation Guide.. or whtever lmao
1. **Clone Repository**:
   ```bash
   git clone https://github.com/username/Stresslezz.git
2. **Buka di Android Studio**:
   File → New → Import Project terus
   Pilih folder root project (yang ada settings.gradle di dalemnya)
3. **Confguration**:
   Tunggu Gradle sync selesai
   File → Project Structure → SDK Location → Pilih JDK 17
4. **Coba liat aplikasinya**:
   Test make emulator dari android studionya

## Cara run di android gmn? 🤔
1. **Build APK**:
   Build → Generate Signed Bundle / APK
   Pilih APK 
   hasilnya biasanya ada di app/build/outputs/apk/debug/app-debug.apk
   
### **Catatan Khusus**  
- **Jangan commit** `local.properties` (file auto-generate)  
- Kalo ada error **"Manifest merger failed"**, cek `AndroidManifest.xml` di semua modul  

---

**📌 Contoh Tampilan di Android Studio:**  

![Home Screen](https://i.imgur.com/1uVATuK.png)

UInya tinggal dimodif kalo mau, di bagian layout ada file xml
