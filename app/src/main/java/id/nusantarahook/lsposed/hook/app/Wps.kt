package id.nusantarahook.lsposed.hook.app

import android.content.Context
import android.content.pm.PackageManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Wps : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val hookClassName = "WpsHook"
        var hookRegistered = false
        //testi
        try {
            // Hook method yang mengecek package premium
            val targetClass = XposedHelpers.findClass(
                "cn.wps.moffice.common.google.pay.PremiumNoInstallActivity",
                lpparam.classLoader
            )

            XposedHelpers.findAndHookMethod(
                targetClass,
                "onResume",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val context = param.thisObject as Context
                        val isPremiumInstalled = try {
                            context.packageManager.getPackageInfo(
                                "cn.wps.moffice_premium",
                                PackageManager.GET_ACTIVITIES
                            )
                            true
                        } catch (e: PackageManager.NameNotFoundException) {
                            false
                        }
                        XposedBridge.log("WpsHook: Premium installed = $isPremiumInstalled")
                    }
                }
            )

            hookRegistered = true
        } catch (e: Exception) {
            XposedBridge.log("WpsHook: Error hooking PremiumNoInstallActivity: $e")
        }

        // Summary log
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName")
        }
    }
}