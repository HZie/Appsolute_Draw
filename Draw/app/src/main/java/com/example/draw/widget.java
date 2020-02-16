package com.example.draw;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class widget extends AppWidgetProvider {



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // tag_id: motive = 101, healing = 102, boring =103, refresh = 104
    public PendingIntent getPendingSelfIntent(Context context, int tag_id){
        Intent intent = new Intent(context, widget2.class);
        intent.setAction("FROM_WIDGET01");
        intent.putExtra("tag",tag_id);
        return PendingIntent.getActivity(context,tag_id,intent,0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context,widget2.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent,0);


            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);

            // 버튼 정의
            views.setOnClickPendingIntent(R.id.widget_imgBtn_motive, getPendingSelfIntent(context,101));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_healing,getPendingSelfIntent(context,102));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_boring, getPendingSelfIntent(context,103));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_refresh, getPendingSelfIntent(context,104));


            appWidgetManager.updateAppWidget(appWidgetId,views);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

