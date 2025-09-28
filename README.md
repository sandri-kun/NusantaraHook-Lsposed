# 🌏 NusantaraHook-Lsposed

**NusantaraHook-Lsposed**

Versi panjang README profesional untuk repositori eksperimen modul **LSPosed** berbasis Kotlin. Project ini dibuat sebagai kerangka kerja modular untuk belajar dan bereksperimen dengan hooking pada Android menggunakan LSPosed, DexKit (native helper), dan Xposed API. Dokumentasi ini ditujukan untuk pengembang yang ingin memahami arsitektur, cara build, struktur kode, dan praktik kontribusi — tetap menekankan penggunaan etis dan legal.

---

## 🔎 Ringkasan

NusantaraHook-Lsposed adalah koleksi modular untuk membuat module LSPosed yang:
- Mudah diperluas — arsitektur plug-and-play untuk menambahkan hook per-package.
- Menggunakan Kotlin modern untuk readability dan maintainability.
- Memungkinkan integrasi library native (contoh: `libdexkit.so`) untuk membantu pencarian method/class runtime.
- Menyediakan pola penanganan error, logging, dan isolasi hook agar stabil pada runtime.

> **Catatan penting (legal & etika)**  
> Gunakan proyek ini hanya untuk tujuan pendidikan, debugging internal, pengujian aplikasi yang Anda miliki hak akses, atau penelitian keamanan yang sah. **Dilarang** menggunakan teknik dalam repo ini untuk membajak, membobol, atau menyalahi privasi/keamanan pihak lain. Pemilik proyek tidak bertanggung jawab atas penyalahgunaan.

---

## ✨ Fitur Utama

- Arsitektur modular: mudah menambahkan `*Hook` baru untuk paket target.
- Entrypoint loader sederhana (`MainHook`) dengan mapping package → module.
- Integrasi native optional (`System.loadLibrary("dexkit")`) dengan fallback aman saat gagal.
- Logging lewat `XposedBridge.log` dan pola logging internal.
- Contoh-contoh hook template untuk aplikasi target: `AnimePlayHook`, `ThunderVpnHook`.
- Struktur yang mendukung pengujian lokal dan debugging via logcat.

---

## 📦 Prasyarat Pengembangan

- Android Studio (disarankan versi terbaru yang stabil)
- JDK 17+ (JDK 21/23 direkomendasikan)
- Android Gradle Plugin sesuai dengan Gradle versi proyek
- Perangkat Android untuk testing (rooted + Magisk + LSPosed untuk pengujian LSPosed)
- Kotlin 1.8+ (sesuaikan dengan konfigurasi project)

**Security reminder:** menguji hooking pada aplikasi pihak ketiga tanpa izin dapat melanggar hukum atau TOS. Selalu uji pada aplikasi Anda sendiri atau dengan persetujuan.

---

## 🛠 Cara Build (Local)

1. Clone repo:
   ```bash
   git clone https://github.com/username/NusantaraHook-Lsposed.git
   cd NusantaraHook-Lsposed
2. Buka di Android Studio:

3. File → Open → pilih folder NusantaraHook-Lsposed.

4. Sinkronisasi Gradle, pastikan gradle.properties dan JDK path sudah sesuai.

5. Assemble release/debug:
