package id.nusantarahook.lsposed.hook.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class FileManager : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.alphainventor.filemanager") return

        val hookClassName = "FileManagerHook"
        var hookRegistered = false

        try {
            val licenseClass = XposedHelpers.findClassIfExists("ax.U1.c", lpparam.classLoader)
            if (licenseClass != null) {
                for (method in licenseClass.declaredMethods) {
                    try {
                        val returnType = method.returnType
                        val hook = when {
                            returnType == Boolean::class.javaPrimitiveType -> {
                                object : XC_MethodHook() {
                                    override fun beforeHookedMethod(param: MethodHookParam) {
                                        param.result = true
                                    }
                                }
                            }

                            returnType.name.contains("License") || returnType.name.contains("Product") -> {
                                object : XC_MethodReplacement() {
                                    override fun replaceHookedMethod(param: MethodHookParam): Any {
                                        return returnType.getDeclaredConstructor().newInstance()
                                    }
                                }
                            }

                            else -> null
                        }
                        if (hook != null) {
                            XposedHelpers.findAndHookMethod(
                                licenseClass,
                                method.name,
                                *method.parameterTypes,
                                hook
                            )
                            hookRegistered = true
                        }
                    } catch (_: Throwable) {
                    }
                }
            }
        } catch (_: Throwable) {
        }

        // Log satu kali hasil hook
        if (hookRegistered) {
            XposedBridge.log("✅ Success: $hookClassName")
        } else {
            XposedBridge.log("❌ Failed: $hookClassName")
        }
    }
}