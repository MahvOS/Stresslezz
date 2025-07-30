package com.mahvin.healthcare.data

import android.content.Context
import com.mahvin.healthcare.model.StepItem
import com.mahvin.stresslezz.R

object StepRepository {
    private val pagiSteps: List<List<StepItem>> = listOf(
        listOf(
            StepItem(1, "Al-Fatihah (1:1)", "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Dengan nama Allah yang Maha Pengasih, Maha Penyayang", "Memulai hari dengan menyebut nama Allah menenangkan hati.", "Bangun, ucapkan bismillah, tarik napas dalam, senyum.", R.drawable.groy),
            StepItem(2, "Al-Baqarah (2:286)", "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا", "Allah tidak membebani seseorang melainkan sesuai kesanggupannya.", "Allah tidak memberi ujian melebihi kemampuan kita.", "Napas 4-7-8, fokus 1 hal yang bisa kamu kontrol hari ini.", R.drawable.groy),
            StepItem(3, "At-Tawbah (9:51)", "قُل لَّن يُصِيبَنَا إِلَّا مَا كَتَبَ اللَّهُ لَنَا", "Tidak akan menimpa kami selain apa yang telah ditentukan Allah bagi kami.", "Menerima takdir mengurangi kecemasan masa depan.", "Ucapkan 'Cukuplah Allah bagiku', ganti pikiran cemas jadi positif.", R.drawable.groy),
            StepItem(4, "Ash-Sharh (94:5-6)", "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "Sesungguhnya bersama kesulitan ada kemudahan.", "Setiap masalah pasti ada solusi.", "Afirmasi: 'Saya siap menghadapi semuanya.' Lakukan PMR.", R.drawable.groy),
            StepItem(5, "An-Nahl (16:128)", "إِنَّ اللَّهَ مَعَ الَّذِينَ اتَّقَوْا وَالَّذِينَ هُمْ مُحْسِنُون", "Allah bersama orang yang bertakwa & berbuat baik.", "Kebaikan memberi ketenangan hati.", "Lakukan 1 kebaikan kecil, seperti memberi senyum/membantu orang lain.", R.drawable.groy),
        ),
        listOf(
            StepItem(6, "Al-Imran (3:173)", "وَقَالُوا حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ", "Cukuplah Allah bagi kami...", "Kepercayaan bahwa Allah adalah pelindung terbaik.", "Bangun, ucapkan dalam hati 'Cukuplah Allah bagiku' lalu teknik napas.", R.drawable.groy),
            StepItem(7, "Al-Araf (7:56)", "وَلَا تُفْسِدُوا فِي الْأَرْضِ...", "Janganlah kamu berbuat kerusakan...", "Jangan biarkan kecemasan merusak ketenangan.", "Lakukan meditasi singkat setelah bangun tidur.", R.drawable.groy),
            StepItem(8, "Al-Furqan (25:63)", "وَعِبَادُ الرَّحْمَٰنِ...", "Hamba-hamba Tuhan yang berjalan tenang...", "Tenang dalam menjalani hidup.", "Berjalan santai atau berdiri tegak dan tarik napas.", R.drawable.groy),
            StepItem(9, "Ar-Ra'd (13:28)", "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ", "Dengan mengingat Allah hati jadi tenang...", "Dzikir menenangkan hati gelisah.", "Luangkan waktu dzikir singkat setelah bangun tidur.", R.drawable.groy),
            StepItem(10, "Al-Ahzab (33:39)", "إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ", "Allah dan malaikat bersalawat untuk Nabi", "Salawat membawa rahmat dan ketenangan.", "Bersalawat setelah bangun tidur.", R.drawable.groy)
        ),
        listOf(
            StepItem(11, "Al-Baqarah (2:199)", "وَفِي رَحْمَتِهِ خَيْرٌ لَّكُمْ", "Dan dalam rahmat-Nya terdapat kebaikan yang lebih besar bagi kalian.", "Allah menawarkan rahmat-Nya lebih baik dari kekhawatiran kita.", "Bangun, lepaskan pikiran negatif, tanamkan keyakinan Allah memberi yang terbaik. Fokus 1 hal positif.", R.drawable.groy),
            StepItem(12, "Al-Baqarah (2:214)", "أَمْ حَسِبْتُمْ أَنْ تَدْخُلُوا الْجَنَّةَ وَمَا يَأْتِيكُمْ مِّنْ مَثَلِ الَّذِينَ خَلَوْا مِنْ قَبْلِكُمْ", "Ataukah kalian mengira akan masuk surga, padahal belum datang kepada kalian seperti orang-orang sebelum kalian?", "Kesulitan bagian dari ujian hidup, hadapi dengan sabar & tawakal.", "Bangun, identifikasi 1 tantangan yang bisa dihadapi dengan sabar & yakin. Fokus pada penerimaan ujian hidup.", R.drawable.groy),
            StepItem(13, "At-Tawbah (9:105)", "وَقُلِ اعْمَلُوا فَسَيَرَى اللَّهُ عَمَلَكُمْ", "Dan katakanlah, 'Bekerjalah kalian, maka Allah akan melihat pekerjaan kalian.'", "Allah menilai usaha kita, hasilnya adalah rahmat-Nya.", "Bangun, tentukan 1 langkah kecil maju (misal: selesaikan tugas kecil). Fokus usaha, biarkan hasil mengikuti.", R.drawable.groy),
            StepItem(14, "Al-Baqarah (2:148)", "لِكُلٍّ وِجْهَةٌ هُوَ مُوَلِّيهَا", "Bagi setiap umat ada arah yang dia tuju.", "Setiap orang punya jalan hidup, percaya kita di jalan yang tepat.", "Bangun, tulis 1 tujuan kecil hari ini (misal: 'Saya akan selesaikan pekerjaan A.'). Fokus hal yang bisa dikontrol, buat rencana sederhana, mulai langkah kecil.", R.drawable.groy),
            StepItem(15, "Al-Mulk (67:15)", "هُوَ الَّذِي جَعَلَ لَكُمُ الْأَرْضَ ذَلُولًا", "Dia-lah yang menjadikan bumi ini mudah untuk kalian.", "Allah menciptakan bumi agar mudah dihadapi.", "Bangun, pilih 1 hal kecil yang bisa kamu kontrol & lakukan untuk buat harimu lebih mudah (misal: merapikan tempat tidur, daftar tugas sederhana).", R.drawable.groy),
        ),
        listOf(
            StepItem(16, "At-Tawbah (9:51)", "قُل لَّن يُصِيبَنَا إِلَّا مَا كَتَبَ اللَّهُ لَنَا", "Katakanlah, ‘Tidak akan menimpa kami selain apa yang telah ditentukan Allah bagi kami.’", "Takdir Allah adalah yang terbaik, perlu menerima segala yang terjadi.", "Bangun, ucapkan 'Cukuplah Allah bagiku', visualisasikan hadapi tantangan tenang. Fokus langkah pertama atasi kecemasan.", R.drawable.groy),
            StepItem(17, "Al-Ankabut (29:69)", "وَالَّذِينَ جَاهَدُوا فِينَا لَنَهْدِيَنَّهُمْ سُبُلَنَا", "Dan orang-orang yang berjihad di jalan Kami, Kami akan tunjukkan kepada mereka jalan-jalan Kami.", "Setiap usaha sungguh-sungguh di jalan Allah dapat petunjuk & kemudahan.", "Bangun, tentukan 1 langkah kecil yang mengurangi kecemasan (misal: mulai tugas tertunda). Fokus langkah pertama tugas itu, jangan khawatir hasil.", R.drawable.groy),
            StepItem(18, "Al-Fajr (89:28)", "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "Karena sesungguhnya bersama kesulitan ada kemudahan.", "Allah menjanjikan kemudahan setelah kesulitan.", "Bangun, saat cemas, lakukan cognitive reframing: ganti 'Hari ini akan sulit' dengan 'Kemudahan akan datang setelah kesulitan.' Fokus 1 hal positif untuk solusi.", R.drawable.groy),
            StepItem(19, "Al-Imran (3:173)", "وَقَالُوا حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ", "Dan mereka berkata, 'Cukuplah Allah bagi kami, dan Dia adalah sebaik-baiknya pelindung.'", "Menyerahkan urusan pada Allah beri ketenangan & kepercayaan.", "Bangun, ucapkan 'Cukuplah Allah bagiku.' Lakukan napas 4-7-8. Fokus ketenangan dengan setiap napas.", R.drawable.groy),
            StepItem(20, "Al-Araf (7:56)", "وَلَا تُفْسِدُوا فِي الْأَرْضِ بَعْدَ إِصْلَحِهَا", "Dan janganlah kamu berbuat kerusakan di muka bumi setelah (Allah) memperbaikinya.", "Jangan biarkan kecemasan merusak kedamaian yang Allah berikan.", "Bangun, meditasi/teknik napas tenangkan tubuh & pikiran. Fokus kedamaian setelah napas dalam. Ingatkan diri tetap tenang.", R.drawable.groy),
        ),
        listOf(
            StepItem(21, "An-Nahl (16:128)", "إِنَّ اللَّهَ مَعَ الَّذِينَ اتَّقَوْا وَالَّذِينَ هُمْ مُحْسِنُونَ", "Sesungguhnya Allah bersama orang-orang yang bertakwa dan orang-orang yang berbuat baik.", "Allah selalu mendampingi orang yang berusaha berbuat baik.", "Bangun, lakukan 1 kebaikan kecil (senyum/bantu orang). Fokus perasaan positif saat berbuat baik, biarkan itu mengurangi kecemasan.", R.drawable.groy),
            StepItem(22, "Az-Zumar (39:10)", "قُلْ يَا عِبَادِيَ الَّذِينَ آمَنُوا إِنَّ رَبَّكُمْ وَاسِعُ الْمَغْفِرَةِ", "Katakanlah, 'Wahai hamba-hamba-Ku yang beriman, sesungguhnya Tuhanmu Maha Luas ampunannya.'", "Allah selalu menerima tobat dan memberikan ampunan yang luas.", "Bangun, beristighfar & maafkan diri atas kesalahan/penyesalan. Rasakan kedamaian saat memaafkan & lepaskan beban.", R.drawable.groy),
            StepItem(23, "Al-Fajr (89:27-28)", "يَا أَيَّتُهَا النَّفْسُ الْمُطْمَئِنَّةُ", "Wahai jiwa yang tenang.", "Allah memanggil jiwa yang tenang untuk kembali kepada-Nya dalam kedamaian.", "Bangun, napas dalam, bayangkan diri dikelilingi kedamaian Allah. Fokus perasaan tenang saat lepaskan kekhawatiran.", R.drawable.groy),
            StepItem(24, "Al-Baqarah (2:195)", "وَفِي سَبِيلِ اللَّـهِ وَلا تَحْلِكُوا أَنفُسَكُمْ", "Dan janganlah kalian membunuh diri kalian sendiri. Sesungguhnya Allah adalah Maha Penyayang kepadamu.", "Allah mengingatkan jaga kesehatan fisik & mental, jangan menyerah.", "Bangun, refleksi diri & berdoa diberi kekuatan atasi tantangan. Fokus 1 hal yang bisa membuat lebih baik (istirahat/peregangan).", R.drawable.groy),
            StepItem(25, "At-Tawbah (9:51)", "قُل لَّن يُصِيبَنَا إِلَّا مَا كَتَبَ اللَّهُ لَنَا", "Katakanlah, ‘Tidak akan menimpa kami selain apa yang telah ditentukan Allah bagi kami.’", "Takdir Allah adalah yang terbaik, perlu menerima segala yang terjadi.", "Bangun, ulangi kalimat ini dalam hati & fokus hal yang bisa dikontrol hari ini (selesaikan tugas/komunikasi positif).", R.drawable.groy),
        ),
        listOf(
            StepItem(26, "Al-Imran (3:173)", "وَقَالُوا حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ", "Dan mereka berkata, 'Cukuplah Allah bagi kami, dan Dia adalah sebaik-baiknya pelindung.'", "Ketika cemas/sulit, Allah pelindung terbaik beri ketenangan.", "Bangun, ucapkan 'Cukuplah Allah bagiku,' lakukan napas 4-7-8. Fokus ketenangan & keyakinan Allah pelindung terbaik.", R.drawable.groy),
            StepItem(27, "Al-Araf (7:56)", "وَلَا تُفْسِدُوا فِي الْأَرْضِ بَعْدَ إِصْلَحِهَا", "Dan janganlah kamu berbuat kerusakan di muka bumi setelah (Allah) memperbaikinya.", "Jangan biarkan kecemasan merusak kedamaian yang Allah berikan.", "Bangun, lakukan mindfulness: duduk tenang, amati perasaan tanpa menghakimi, terima semua perasaan. Fokus kedamaian saat menerima kenyataan.", R.drawable.groy),
            StepItem(28, "Al-Furqan (25:63)", "وَعِبَادُ الرَّحْمَٰنِ الَّذِينَ يَمْشُونَ عَلَى الْأَرْضِ هَوْنًا", "Dan hamba-hamba Tuhan Yang Maha Pengasih itu adalah orang-orang yang berjalan di bumi dengan rendah hati.", "Allah memuji orang yang berjalan tenang & rendah hati, tunjukkan kedamaian.", "Bangun, coba jalan santai/berdiri tegak, napas dalam & lepaskan ketegangan. Fokus pernapasan & ketenangan tubuh.", R.drawable.groy),
            StepItem(29, "Ar-Ra'd (13:28)", "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ", "Ingatlah, hanya dengan mengingat Allah hati menjadi tenteram.", "Ingat Allah dapat menenangkan hati yang gelisah.", "Bangun, luangkan waktu dzikir (Subhanallah/Alhamdulillah) penuh perhatian. Fokus ketenangan bersama dzikir, biarkan kecemasan mereda.", R.drawable.groy),
            StepItem(30, "Al-Ahzab (33:39)", "إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ", "Sesungguhnya Allah dan malaikat-malaikat-Nya bersalawat untuk Nabi.", "Salawat kepada Nabi Muhammad SAW membawa rahmat dan ketenangan.", "Bangun, bersalawat kepada Nabi Muhammad SAW, rasakan kedamaian. Gunakan NLP kaitkan perasaan positif dengan salawat. Ulangi salawat saat cemas.", R.drawable.groy)
        )
    )

    fun getMorningSteps(context: Context): List<StepItem> {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val index = prefs.getInt("pagi_session_index", 0)
        return pagiSteps[index % pagiSteps.size]
    }

    fun advanceMorningSession(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentIndex = prefs.getInt("pagi_session_index", 0)
        prefs.edit().putInt("pagi_session_index", (currentIndex + 1) % pagiSteps.size).apply()
    }
}
