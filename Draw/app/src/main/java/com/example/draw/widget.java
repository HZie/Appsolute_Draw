package com.example.draw;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class widget extends AppWidgetProvider {
    static String tag = "tag";
    static int widget_screen = 1;

    // tag_id: motive = 101, healing = 102, boring =103, refresh = 104, etc = 201
    // situation: widget layout 1 --> 2 = 12, widget layout 2 --> 1 = 21, refresh = 22
    public PendingIntent getPendingIntent(Context context, int tag_id, int situation){
        Intent intent = new Intent(context,widget.class);

        if(situation == 12){
            intent.setAction("android.action.TAG_BUTTON");
            intent.putExtra("tag",tag_id);
        }
        else if(situation == 21){
            intent.setAction("android.action.BACK_BUTTON");

        }
        else if(situation == 22){
            intent.setAction("android.action.REFRESH_BUTTON");
        }

        return PendingIntent.getBroadcast(context,tag_id,intent,0);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

            widget_screen = 1;
            setLayout(views,widget_screen);

            // 버튼 정의
            views.setOnClickPendingIntent(R.id.widget_imgBtn_motive, getPendingIntent(context,101,12));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_healing,getPendingIntent(context,102,12));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_boring, getPendingIntent(context,103,12));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_refresh, getPendingIntent(context,104,12));

            views.setOnClickPendingIntent(R.id.widget_imgBtn_back,getPendingIntent(context, 201,21));
            views.setOnClickPendingIntent(R.id.widget_text,getPendingIntent(context,201,22));

            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }


    public void setLayout(RemoteViews views, int widget_screen){
        if(widget_screen==1){
            views.setViewVisibility(R.id.widget_layout_01,View.VISIBLE);
            views.setViewVisibility(R.id.widget_layout_02,View.GONE);
        }
        else if(widget_screen == 2){
            views.setViewVisibility(R.id.widget_layout_01,View.GONE);
            views.setViewVisibility(R.id.widget_layout_02,View.VISIBLE);
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


    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);

        String TAG_ACTION = "android.action.TAG_BUTTON";
        String BACK_ACTION = "android.action.BACK_BUTTON";
        String REFRESH_ACTION = "android.action.REFRESH_BUTTON";
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        // 수신한 인텐트로부터 데이터 읽기
        String action = intent.getAction();
        int tag_id = 0;

        // tag_id: motive = 101, healing = 102, boring =103, refresh = 104
        if(action.equals(TAG_ACTION)) {
            tag_id = intent.getIntExtra("tag", 0);
            widget_screen = 2;
            switch (tag_id) {
                case 101:
                    tag = "motive";
                    break;
                case 102:
                    tag = "healing";
                    break;
                case 103:
                    tag = "boring";
                    break;
                case 104:
                    tag = "refresh";
                    break;
                default:
                    break;
            }



        }

        if(action.equals(BACK_ACTION)){
            widget_screen = 1;
        }

        if(action.equals(REFRESH_ACTION)){
            widget_screen = 2;
        }

        setLayout(views, widget_screen);

        if(action.equals(TAG_ACTION) || action.equals(REFRESH_ACTION)){
            // 텍스트 불러오기
            views.setTextViewText(R.id.widget_text, readData(context, tag));
        }

        // 위젯 화면 갱신
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context,widget.class);
        appWidgetManager.updateAppWidget(cpName,views);

    }

    // 데이터 읽기
    public String readData(Context context,String tag){
        String content = "No Data";
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = context.getSharedPreferences("sFile",MODE_PRIVATE);
        int fileNum = 0;

        //만약 파일에 값이 없다면? num은 0으로 그냥 두기.
        try{
            switch(tag){
                case "motive": fileNum = Integer.parseInt(sf.getString("mNum","0")); break;
                case "boring" : fileNum = Integer.parseInt(sf.getString("bNum","0")); break;
                case "healing": fileNum = Integer.parseInt(sf.getString("hNum","0")); break;
                case "refresh" : fileNum = Integer.parseInt(sf.getString("rNum","0")); break;
            }
        }catch(Exception e){
            return content;
        }

        int random = (int)(Math.random() * fileNum)+1;

        content = sf.getString(tag+random,"No data");

        return content;
    }

}

