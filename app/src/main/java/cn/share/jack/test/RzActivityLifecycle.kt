//package radiancez.android.app.activity.lifecycle
//
//import android.app.Activity
//import android.app.Application
//import android.os.Bundle
//import radiancez.android.app.activity.lifecycle.event.RzAppBackgroundEvent
//import radiancez.android.app.activity.lifecycle.event.RzAppForegroundEvent
//import radiancez.eventbus.RzEventBus
//import radiancez.eventbus.RzEventBusUtil
//import java.util.*
//
///**
// * Created by radiance on 2018/5/15.
// * RzActivityLifecycle
// */
//
//object RzActivityLifecycle : Application.ActivityLifecycleCallbacks {
//
//    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        activities.add(activity)
//        RzEventBusUtil.registerByAnnotation(activity)
//    }
//
//    override fun onActivityStarted(activity: Activity) {
//        val appForeground = isAppForeground
//        foregroundActivityCount++
//        if (isAppForeground != appForeground) {
//            RzEventBus.post(RzAppForegroundEvent())
//        }
//    }
//
//    override fun onActivityResumed(activity: Activity) {
//    }
//
//    override fun onActivityPaused(activity: Activity) {
//    }
//
//    override fun onActivityStopped(activity: Activity) {
//        val appForeground = isAppForeground
//        foregroundActivityCount--
//        if (isAppForeground != appForeground) {
//            RzEventBus.post(RzAppBackgroundEvent())
//        }
//    }
//
//    override fun onActivityDestroyed(activity: Activity) {
//        RzEventBusUtil.unregisterByAnnotation(activity)
//        activities.remove(activity)
//    }
//
//    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//    @JvmStatic val activities: MutableSet<Activity> = HashSet()
//    @JvmStatic val lastActivity: Activity?
//        get() = activities.lastOrNull()
//
//    @JvmStatic val isAppForeground: Boolean
//        get() = foregroundActivityCount > 0
//    @JvmStatic private var foregroundActivityCount: Int = 0
//}
