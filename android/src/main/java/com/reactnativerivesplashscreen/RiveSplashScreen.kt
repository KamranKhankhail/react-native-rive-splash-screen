package org.devio.rn.splashscreen

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.view.WindowManager

/**
 * RiveSplashScreen
 * Author:CrazyCodeBoy, |||| translated to kotlin by KamranKhanKhail
 * GitHub:https://github.com/crazycodeboy |||| https://github.com/KamranKhankhail
 */
public object RiveSplashScreen {
  private var mSplashDialog: Dialog? = null
  private var mActivity: WeakReference<Activity>? = null

  /**
   * 打开启动屏
   */
  fun show(activity: Activity?, themeResId: Int, fullScreen: Boolean) {
    if (activity == null) return
    mActivity = WeakReference<Activity>(activity)
    activity.runOnUiThread(object : Runnable {
      override fun run() {
        if (!activity.isFinishing()) {
          mSplashDialog = Dialog(activity, themeResId)
          mSplashDialog.setContentView(R.layout.launch_screen)
          mSplashDialog.setCancelable(false)
          if (fullScreen) {
            setActivityAndroidP(mSplashDialog)
          }
          if (!mSplashDialog.isShowing()) {
            mSplashDialog.show()
          }
        }
      }
    })
  }

  /**
   * 打开启动屏
   */
  fun show(activity: Activity?, fullScreen: Boolean) {
    val resourceId: Int = if (fullScreen) R.style.SplashScreen_Fullscreen else R.style.SplashScreen_SplashTheme
    show(activity, resourceId, fullScreen)
  }

  /**
   * 打开启动屏
   */
  fun show(activity: Activity?) {
    show(activity, false)
  }

  /**
   * 关闭启动屏
   */
  fun hide(activity: Activity?) {
    var activity: Activity? = activity
    if (activity == null) {
      if (mActivity == null) {
        return
      }
      activity = mActivity.get()
    }
    if (activity == null) return
    val _activity: Activity = activity
    _activity.runOnUiThread(object : Runnable {
      override fun run() {
        if (mSplashDialog != null && mSplashDialog.isShowing()) {
          var isDestroyed = false
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed = _activity.isDestroyed()
          }
          if (!_activity.isFinishing() && !isDestroyed) {
            mSplashDialog.dismiss()
          }
          mSplashDialog = null
        }
      }
    })
  }

  private fun setActivityAndroidP(dialog: Dialog?) {
    //设置全屏展示
    if (Build.VERSION.SDK_INT >= 28) {
      if (dialog != null && dialog.getWindow() != null) {
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //全屏显示
        val lp: WindowManager.LayoutParams = dialog.getWindow().getAttributes()
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        dialog.getWindow().setAttributes(lp)
      }
    }
  }
}
