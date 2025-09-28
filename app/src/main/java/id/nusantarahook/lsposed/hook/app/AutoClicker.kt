package id.nusantarahook.lsposed.hook.app

import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class AutoClicker : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.truedevelopersstudio.automatictap.autoclicker") return

        var hookRegistered = false
        val hookClassName = "AutoClickerHook"

        // Hook Y0(boolean z)
        try {
            val clazz = XposedHelpers.findClassIfExists(
                "com.truedevelopersstudio.autoclicker.activities.MainActivity",
                lpparam.classLoader
            )
            if (clazz != null) {
                XposedHelpers.findAndHookMethod(
                    clazz,
                    "Y0",
                    Boolean::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            param.args[0] = true
                        }
                    }
                )
                hookRegistered = true
            }
        } catch (_: Throwable) {
        }

        // Hook f.a(Context)
        try {
            val fClass = XposedHelpers.findClassIfExists("i2.f", lpparam.classLoader)
            if (fClass != null) {
                XposedHelpers.findAndHookMethod(
                    fClass,
                    "a",
                    Context::class.java,
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            param.result = true
                        }
                    }
                )
                hookRegistered = true
            }
        } catch (_: Throwable) {
        }

        // Log akhir
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName")
        }
    }
}