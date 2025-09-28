package id.nusantarahook.lsposed.hook.app

/*
 * Copyright (C) 2019-2023 James Clef <qwq233@qwq2333.top>
 * https://github.com/qwq233/FuckDeveloperAssistant
 *
 * This software is open source software BUT IT IS NOT FREE SOFTWARE:
 * you can redistribute it and/or modify it under our terms.
 */

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import org.luckypray.dexkit.DexKitBridge
import java.lang.reflect.Modifier

class DeveloperAssistant : IXposedHookLoadPackage {
    private val doExportDex = false // Debug only
    private lateinit var dexKit: DexKitBridge

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        runCatching {
            if (lpparam.packageName != "com.appsisle.developerassistant") return@runCatching

            val classloader = lpparam.classLoader
            dexKit = DexKitBridge.create(classloader, true)

            loge("find licenseStatus")
            val licenseStatus = dexKit.findClass {
                matcher {
                    addMethod {
                        name = "toString"
                        usingStrings = listOf("LicenseStatus(license=", ", expiryDate=")
                    }
                }
            }.single().getInstance(classloader).run {
                getPermanentLicense(dexKit, classloader, this).also { licenseObj ->
                    // ganti hookAllConstructorBefore
                    for (ctor in this.constructors) {
                        XposedBridge.hookMethod(ctor, object : XC_MethodHook() {
                            override fun beforeHookedMethod(param: MethodHookParam) {
                                param.result = licenseObj
                            }
                        })
                    }
                }
            }

            loge("hook licenseManager")
            dexKit.findClass {
                matcher {
                    usingStrings = listOf(
                        "refreshLicenseIfNotRefreshedRecently",
                        "refreshLicense",
                        "onLicenseRefreshFailed"
                    )
                }
            }.single().getInstance(classloader).declaredMethods.single {
                it.parameterTypes.size == 1 && it.returnType.name == it.parameterTypes[0].name
            }.let { method ->
                // ganti hookBefore
                XposedBridge.hookMethod(method, object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = licenseStatus
                    }
                })
            }
        }.onFailure {
            loge(it.message ?: "Unknown error")
            loge(it.stackTraceToString())
            dexKit.close()
        }.onSuccess {
            dexKit.close()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getPermanentLicense(
        dexkit: DexKitBridge,
        classLoader: ClassLoader,
        clazz: Class<*>,
    ): Any =
        dexkit.findClass {
            matcher {
                usingStrings = listOf(
                    "Permanent", "Subscription", "Evaluation",
                    "Expired", "NotKnownYet", "Invalid"
                )
            }
        }.single().getInstance(classLoader).run result@{
            (declaredFields.single {
                it.type.isArray && it.isSynthetic && Modifier.isStatic(it.modifiers)
            }.apply {
                isAccessible = true
            }.get(null) as Array<Enum<*>>).forEach {
                if (it.name == "Permanent") {
                    return@result clazz.constructors.single { c ->
                        c.parameterTypes.size == 1
                    }.apply {
                        isAccessible = true
                    }.newInstance(it)
                }
            }
        }

    private fun loge(msg: String) {
        Log.e("FuckDeveloperAssistant", msg)
        try {
            XposedBridge.log("FuckDeveloperAssistant: $msg")
        } catch (e: NoClassDefFoundError) {
            Log.e("Xposed", msg)
            Log.e("EdXposed-Bridge", msg)
        }
    }
}
