package com.maden.makewidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.lifecycle.*
import com.maden.makewidget.model.count_model.CountModel
import com.maden.makewidget.service.BackgroundRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ON_CLICK_TAG = "ON_CLICK_TAG"

open class RandomWidget : AppWidgetProvider(), LifecycleObserver {

    var appWidgetIdArray: IntArray? = null

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        val count = appWidgetIds.size
        appWidgetIdArray = appWidgetIds


        for (i in 0 until count) {
            val widgetId = appWidgetIds[i]

            val remoteViews = RemoteViews(context.packageName, R.layout.random_widget)

            val intent = Intent(context, RandomWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setOnClickPendingIntent(
                R.id.activeText,
                getPendingSelfIntent(context, ON_CLICK_TAG)
            )

            remoteViews.setOnClickPendingIntent(
                R.id.completeText,
                getPendingSelfIntent(context, ON_CLICK_TAG)
            )

            remoteViews.setOnClickPendingIntent(
                R.id.waitText,
                getPendingSelfIntent(context, ON_CLICK_TAG)
            )

            remoteViews.setOnClickPendingIntent(
                R.id.allText,
                getPendingSelfIntent(context, ON_CLICK_TAG)
            )
            remoteViews.setOnClickPendingIntent(
                R.id.reload,
                getPendingSelfIntent(context, ON_CLICK_TAG)
            )

            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }

    }

    protected open fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, RandomWidget::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val remoteViews = RemoteViews(context.packageName, R.layout.random_widget)
        if (intent.action == ON_CLICK_TAG) {

            val repo = BackgroundRepo()
            CoroutineScope(Dispatchers.IO).launch {
                val auth = repo.login()
                if (auth != "") {
                    val countModel: CountModel? = repo.getCount(auth)
                    countModel?.let {
                        it.workOrderCount?.let { c ->
                            remoteViews.setTextViewText(
                                R.id.activeText,
                                c.activeWorkOrderCount.toString()
                            )
                            remoteViews.setTextViewText(
                                R.id.completeText,
                                c.completeWorkOrderCount.toString()
                            )
                            remoteViews.setTextViewText(
                                R.id.failText,
                                c.failedWorkOrderCount.toString()
                            )
                            remoteViews.setTextViewText(
                                R.id.waitText,
                                c.waitWorkOrderCount.toString()
                            )
                            remoteViews.setTextViewText(
                                R.id.allText,
                                "Tümü: " + c.allWorkOrderCount.toString()
                            )
                        }
                    }
                }

                val appWidgetManager = AppWidgetManager.getInstance(context)
                val thisWidget = ComponentName(context, RandomWidget::class.java)
                val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)

                for (widgetId in allWidgetIds) {
                    appWidgetManager.updateAppWidget(widgetId, remoteViews)
                }
            }
        }
    }
}