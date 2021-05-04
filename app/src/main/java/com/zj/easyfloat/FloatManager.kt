package com.zj.easyfloat

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.imuxuan.floatingview.EnFloatingView
import com.imuxuan.floatingview.FloatingView

object FloatManager : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        initShow(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        FloatingView.get().add()
    }

    override fun onActivityPaused(activity: Activity) {
        FloatingView.get().remove()
    }

    override fun onActivityStopped(activity: Activity) {
        FloatingView.get().detach(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    private fun getFloatingLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.START
        params.setMargins(0, params.topMargin, params.rightMargin, 500)
        return params
    }

    private fun initShow(activity: Activity) {
        activity.let {
            FloatingView.get().customView(
                EnFloatingView(activity, R.layout.layout_float_view)
            )
            FloatingView.get().layoutParams(getFloatingLayoutParams())
            FloatingView.get().attach(it)
        }
    }

    fun show(activity: Activity) {
        initShow(activity)
        activity.application.registerActivityLifecycleCallbacks(this)
    }

    fun dismiss(activity: Activity) {
        FloatingView.get().remove()
        FloatingView.get().detach(activity)
        activity.application.unregisterActivityLifecycleCallbacks(this)
    }
}