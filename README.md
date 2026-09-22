# 🌏 NusantaraHook-Lsposed

**NusantaraHook-Lsposed**

This project is created as a modular framework for learning and experimenting with hooking on Android using LSPosed, DexKit (native helper), and the Xposed API. This documentation is intended for developers who want to understand the architecture, how to build, the code structure, and contribution practices — while still emphasizing ethical and legal use.

---

## 🔎 Overview

NusantaraHook-Lsposed is a modular collection for creating LSPosed modules that:
- Are easily extensible — plug-and-play architecture for adding per-package hooks.
- Use modern Kotlin for readability and maintainability.
- Allow integration of native libraries (e.g., `libdexkit.so`) to assist with runtime method/class discovery.
- Provide patterns for error handling, logging, and hook isolation to remain stable at runtime.

> **Important note (legal & ethics)**  
> Use this project only for educational purposes, internal debugging, testing applications you have access rights to, or legitimate security research. **It is forbidden** to use the techniques in this repo to hijack, break into, or violate the privacy/security of others. The project owner is not responsible for misuse.

---

## ✨ Key Features

- Modular architecture: easy to add new `*Hook` for target packages.
- Simple entrypoint loader (`MainHook`) with package → module mapping.
- Optional native integration (`System.loadLibrary("dexkit")`) with a safe fallback on failure.
- Logging via `XposedBridge.log` and internal logging patterns.
- Example hook templates for target applications: `AnimePlayHook`, `ThunderVpnHook`.
- Structure that supports local testing and debugging via logcat.

---

## 📦 Development Prerequisites

- Android Studio (latest stable version recommended)
- JDK 17+ (JDK 21/23 recommended)
- Android Gradle Plugin matching the project's Gradle version
- Android device for testing (rooted + Magisk + LSPosed for LSPosed testing)
- Kotlin 1.8+ (adjust to project configuration)

**Security reminder:** testing hooking on third-party applications without permission may violate the law or TOS. Always test on your own applications or with consent.

---

## 🛠 How to Build (Local)

1. Clone the repo:
   ```bash
   git clone https://github.com/username/NusantaraHook-Lsposed.git
   cd NusantaraHook-Lsposed
   ```
2. Open in Android Studio:
   File → Open → select the NusantaraHook-Lsposed folder.

3. Sync Gradle, make sure gradle.properties and the JDK path are correct.

4. Build the APK
   ```bash
   ./gradlew :app:assembleRelease
   ```

5. Install to device
   After a successful build, install the module .apk to the Android device that already has LSPosed installed.

## 🛠️ How to Use

1. Make sure the device is rooted with Magisk + LSPosed.
2. Install NusantaraHook-Lsposed.apk as an LSPosed module.
3. Enable the module for the target application
4. Other applications can be added via code.
   ``` Reboot the target application, then check logcat / LSPosed log to make sure the hook succeeded.
   ```

> ⚠️ Disclaimer
> ⚠️ Warning
> This project is created for educational and research purposes only.
> Use for breaking into applications, bypassing security, or illegal purposes is strictly prohibited.
> The developer is not responsible for any misuse.
