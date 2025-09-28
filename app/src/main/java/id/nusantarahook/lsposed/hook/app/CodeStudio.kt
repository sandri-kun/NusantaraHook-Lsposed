package id.nusantarahook.lsposed.hook.app

import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class CodeStudio : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.alif.ide") return

        val hookClassName = "CodeStudioHook"
        var hookRegistered = false

        try {
            val targetClass = XposedHelpers.findClassIfExists("l6.b", lpparam.classLoader)
            if (targetClass != null) {
                val methods = targetClass.declaredMethods
                for (method in methods) {
                    val params = method.parameterTypes
                    val returnType = method.returnType

                    if (params.size == 1 && params[0] == Context::class.java && returnType == Boolean::class.javaPrimitiveType) {
                        try {
                            XposedHelpers.findAndHookMethod(
                                targetClass,
                                method.name,
                                Context::class.java,
                                object : XC_MethodHook() {
                                    override fun beforeHookedMethod(param: MethodHookParam) {
                                        param.result = true
                                    }
                                }
                            )
                            hookRegistered = true // hook berhasil didaftarkan
                        } catch (_: Throwable) {
                            // gagal hook method tertentu, lanjut ke method lain
                        }
                    }
                }
            }
        } catch (_: Throwable) {
            // tetap lanjut ke log akhir
        } finally {
            if (hookRegistered) {
                XposedBridge.log("✅ Success: $hookClassName") // minimal satu hook berhasil didaftarkan
            } else {
                XposedBridge.log("❌ Failed: $hookClassName")
            }
        }
    }
}