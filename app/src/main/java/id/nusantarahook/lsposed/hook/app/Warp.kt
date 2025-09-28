package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Warp : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val hookClassName = "WarpHook"
        var hookRegistered = false

        // Pengecekan package tambahan
        if (lpparam.packageName != "com.cloudflare.app" &&
            lpparam.packageName != "com.cloudflare.onedotonedotonedotone"
        ) {
            return
        }

        try {
            val classLoader = lpparam.classLoader
            val accountDataClass =
                XposedHelpers.findClass("com.cloudflare.app.data.warpapi.AccountData", classLoader)
            val warpPlusStateClass = XposedHelpers.findClass(
                "com.cloudflare.app.data.warpapi.WarpPlusState",
                classLoader
            )

            val unlimitedState by lazy {
                XposedHelpers.getStaticObjectField(
                    warpPlusStateClass,
                    "UNLIMITED"
                )
            }

            for (ctor in accountDataClass.constructors) {
                try {
                    XposedBridge.hookMethod(ctor, object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            try {
                                XposedHelpers.setObjectField(param.thisObject, "b", unlimitedState)
                            } catch (_: Throwable) {
                            }
                        }
                    })
                    hookRegistered = true
                } catch (_: Throwable) {
                }
            }
        } catch (_: Throwable) {
        }

        // Log hasil hook hanya sekali
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName (${lpparam.packageName})")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName (${lpparam.packageName})")
        }
    }
}