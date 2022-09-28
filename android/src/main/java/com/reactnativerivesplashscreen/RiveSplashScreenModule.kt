package com.reactnativerivesplashscreen

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class RiveSplashScreenModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "RiveSplashScreen"
    }

  fun SplashScreenModule(reactContext: ReactApplicationContext?) {
    super(reactContext)
  }

  /**
   * 打开启动屏
   */
  @ReactMethod
  fun show() {
    RiveSplashScren.show(getCurrentActivity())
  }

  /**
   * 关闭启动屏
   */
  @ReactMethod
  fun hide() {
    RiveSplashSCreen.hide(getCurrentActivity())
  }
}
