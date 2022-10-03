package com.reactnativerivesplashscreen

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.view.WindowManager
import app.rive.runtime.kotlin.RiveAnimationView
import java.lang.ref.WeakReference

/**
 * RiveSplashScreen
 * Author:CrazyCodeBoy, |||| translated to kotlin by KamranKhanKhail
 * GitHub:https://github.com/crazycodeboy |||| https://github.com/KamranKhankhail
 */
class RiveSplashScreen {
  companion object {
    private var mSplashDialog: Dialog? = null
    private var mActivity: WeakReference<Activity>? = null
    var splashScreenDisplayedAt: Long = System.currentTimeMillis();


    /**
     * 打开启动屏
     */
    fun show(activity: Activity?, themeResId: Int, fullScreen: Boolean , riveArtboardName: String = "") {
      if (activity == null) return
      mActivity = WeakReference(activity)
      activity.runOnUiThread(Runnable {
        if (!activity.isFinishing) {
          mSplashDialog = Dialog(activity, themeResId)
          //set rive art board name for give view in launch screen
          mSplashDialog!!.setContentView(R.layout.launch_screen)
          if(!riveArtboardName.isNullOrEmpty()) {
            val riveAnimationView: RiveAnimationView = mSplashDialog!!.findViewById<RiveAnimationView>(R.id.rive_animation_view)
            riveAnimationView.artboardName = riveArtboardName;
          }
          mSplashDialog!!.setCancelable(false)
          if (fullScreen) {
            setActivityAndroidP(mSplashDialog)
          }
          if (!mSplashDialog!!.isShowing) {
            mSplashDialog!!.show()
          }
          splashScreenDisplayedAt = System.currentTimeMillis();
        }
      })
    }
    /**
     * 打开启动屏
     */
    /**
     * 打开启动屏
     */
    @JvmOverloads
    fun show(activity: Activity?, fullScreen: Boolean = false , riveArtboardName: String = "") {
      val resourceId = if (fullScreen) R.style.SplashScreen_Fullscreen else R.style.SplashScreen_SplashTheme
      show(activity, resourceId, fullScreen, riveArtboardName)
    }

    /**
     * 关闭启动屏
     */
    fun hide(activity: Activity?) {
      var activity = activity
      if (activity == null) {
        if (mActivity == null) {
          return
        }
        activity = mActivity!!.get()
      }
      if (activity == null) return
      val _activity: Activity = activity
      _activity.runOnUiThread {
        if (mSplashDialog != null && mSplashDialog!!.isShowing) {
          var isDestroyed = false
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed = _activity.isDestroyed
          }
          if (!_activity.isFinishing && !isDestroyed) {
            mSplashDialog!!.dismiss()
          }
          mSplashDialog = null
        }
      }
    }

    private fun setActivityAndroidP(dialog: Dialog?) {
      //设置全屏展示
      if (Build.VERSION.SDK_INT >= 28) {
        if (dialog != null && dialog.window != null) {
          dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //全屏显示
          val lp = dialog.window!!.attributes
          lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
          dialog.window!!.attributes = lp
        }
      }
    }
  }
}
