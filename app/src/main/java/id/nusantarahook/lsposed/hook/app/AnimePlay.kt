package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.util.Calendar

class AnimePlay : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val hookClassName = "AnimePlayHook"
        var hookRegistered = false

        try {
            // Hook User class
            val userClass =
                XposedHelpers.findClass("dev.animeplay.app.models.User", lpparam.classLoader)

            // Hook getIsPremium
            XposedHelpers.findAndHookMethod(userClass, "getIsPremium", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

            // Hook getVerified
            XposedHelpers.findAndHookMethod(userClass, "getVerified", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

            hookRegistered = true
        } catch (_: Throwable) {
        }

        try {
            // Hook UserSetting class
            val userSettingClass =
                XposedHelpers.findClass("dev.animeplay.app.models.UserSetting", lpparam.classLoader)

            XposedHelpers.findAndHookMethod(
                userSettingClass,
                "getExpiryDate",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.YEAR, 9999) // tanggal sangat jauh
                        calendar.set(Calendar.MONTH, Calendar.DECEMBER)
                        calendar.set(Calendar.DAY_OF_MONTH, 31)
                        param.result = calendar.time
                    }
                })

            hookRegistered = true
        } catch (_: Throwable) {
        }

        // Log satu kali hasil hook
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName 🌸🤡")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName")
        }
    }
}