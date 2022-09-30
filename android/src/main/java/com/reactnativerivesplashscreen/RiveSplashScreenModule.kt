package com.reactnativerivesplashscreen

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class RiveSplashScreenModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "RiveSplashScreen"
    }

  /**
   * 打开启动屏
   */
  @ReactMethod
  fun show() {
    RiveSplashScreen.show(getCurrentActivity())
  }

  /**
   * 关闭启动屏
   */
  @ReactMethod
  fun hide() {
    RiveSplashScreen.hide(getCurrentActivity())
  }
}
