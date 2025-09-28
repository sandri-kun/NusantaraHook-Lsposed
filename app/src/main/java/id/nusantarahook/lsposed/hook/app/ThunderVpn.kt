package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import java.util.Calendar

class ThunderVpn : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        val hookClassName = "ThunderVpnHook"
        var hookRegistered = false

        try {
            val vpnUserClass =
                XposedHelpers.findClass("com.signallab.thunder.model.VpnUser", lpparam.classLoader)

            // Hook isVip → true
            XposedHelpers.findAndHookMethod(vpnUserClass, "isVip", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

            // Hook expire → tahun 2099
            XposedHelpers.findAndHookMethod(vpnUserClass, "expire", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val cal = Calendar.getInstance().apply { set(Calendar.YEAR, 9999) }
                    param.result = cal.timeInMillis
                }
            })

            // Hook serviceNow → waktu saat ini
            XposedHelpers.findAndHookMethod(vpnUserClass, "serviceNow", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = System.currentTimeMillis()
                }
            })

            // Hook usingPlan → VIP
            XposedHelpers.findAndHookMethod(vpnUserClass, "usingPlan", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = 1
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