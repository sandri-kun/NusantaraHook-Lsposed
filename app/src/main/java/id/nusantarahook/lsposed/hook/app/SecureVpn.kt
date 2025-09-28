package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class SecureVpn : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        val hookClassName = "SecureVpnHook"
        var hookRegistered = false

        try {
            val vpnUserClass = XposedHelpers.findClass(
                "com.signallab.secure.model.VpnUser",
                lpparam.classLoader
            )

            // Hook isVip
            XposedHelpers.findAndHookMethod(vpnUserClass, "isVip", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

            // Hook expire → Long.MAX_VALUE
            XposedHelpers.findAndHookMethod(vpnUserClass, "expire", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = Long.MAX_VALUE
                }
            })

            // Hook serviceNow → waktu saat ini
            XposedHelpers.findAndHookMethod(vpnUserClass, "serviceNow", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = System.currentTimeMillis() / 1000L
                }
            })

            // Hook usingPlan → 0 (kode VIP)
            XposedHelpers.findAndHookMethod(vpnUserClass, "usingPlan", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = 0
                }
            })

            hookRegistered = true
        } catch (_: Throwable) {
        }

        // Log satu kali hasil hook
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName 🎉")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName")
        }
    }
}