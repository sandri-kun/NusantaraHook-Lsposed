package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class Donghub : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if (lpparam.packageName != "com.anichin.donghub") return

        try {
            // Hook method yang benar-benar mengecek premium di b$c
            val clazzB = XposedHelpers.findClass("h6.b\$c", lpparam.classLoader)

            // Hook getter isPremium (misal g())
            XposedHelpers.findAndHookMethod(
                clazzB,
                "g",
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = true
                        XposedBridge.log("DonghubPremiumSafeHook: b\$c.g() hooked ✅")
                    }
                }
            )

            // Hook singleton UserIsPremium (kelas j) hanya saat dicek equals()
            val clazzJ = XposedHelpers.findClass("z5.j", lpparam.classLoader)
            XposedHelpers.findAndHookMethod(
                clazzJ,
                "equals",
                Any::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = true
                        XposedBridge.log("DonghubPremiumSafeHook: j.equals() hooked ✅")
                    }
                }
            )

            // Hook g() jika ada getter untuk premium di singleton
            try {
                XposedHelpers.findAndHookMethod(
                    clazzJ,
                    "g",
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            param.result = true
                            XposedBridge.log("DonghubPremiumSafeHook: j.g() hooked ✅")
                        }
                    }
                )
            } catch (_: Throwable) {
                // Jika method g() tidak ada, ignore
            }

        } catch (e: Throwable) {
            XposedBridge.log("DonghubPremiumSafeHook error ❌: $e")
        }
    }
}