package id.nusantarahook.lsposed.hook.app

import android.os.Handler
import android.os.Looper
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MusicSpeed : IXposedHookLoadPackage {
    private var hooked = false

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.smp.musicspeed") return

//masih force close

        try {
            val appClass = XposedHelpers.findClass(
                "com.smp.musicspeed.MusicSpeedChangerApplication",
                lpparam.classLoader
            )

            XposedHelpers.findAndHookMethod(appClass, "onCreate", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    // Jalankan sekali saja
                    if (hooked) return
                    hooked = true

                    // Pakai handler agar aman, tunggu beberapa ms supaya semua field sudah siap
                    Handler(Looper.getMainLooper()).post {
                        try {
                            val eValue = XposedHelpers.getStaticBooleanField(appClass, "e")
                            val fValue = XposedHelpers.getStaticBooleanField(appClass, "f")
                            Log.i("MusicSpeedHook", "e = $eValue, f = $fValue")
                        } catch (t: Throwable) {
                            Log.e("MusicSpeedHook", "Failed to read e/f, ignoring", t)
                        }
                    }
                }
            })

            Log.i("MusicSpeedHook", "Hook registered successfully ✅")
        } catch (ex: Throwable) {
            Log.e("MusicSpeedHook", "Failed to register hook ❌", ex)
        }
    }
}