package com.example.draw;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;

/**
 * Implementation of App Widget functionality.
 */
public class widget extends AppWidgetProvider {


    // tag_id: motive = 101, healing = 102, boring =103, refresh = 104
    public PendingIntent getPendingIntent(Context context, int tag_id){
        Log.d(this.getClass().getName(),"button "+tag_id+" is set");
        Intent intent = new Intent(context,widget.class);
        intent.setAction("android.action.WIDGET_BUTTON");
        intent.putExtra("tag",tag_id);
        return PendingIntent.getBroadcast(context,tag_id,intent,0);
    }
    int widget_screen = 1;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = setRemoteViews(context, widget_screen);

            Log.d(this.getClass().getName(),"widget is updated");
            // 버튼 정의
            views.setOnClickPendingIntent(R.id.widget_imgBtn_motive, getPendingIntent(context,101));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_healing,getPendingIntent(context,102));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_boring, getPendingIntent(context,103));
            views.setOnClickPendingIntent(R.id.widget_imgBtn_refresh, getPendingIntent(context,104));


            appWidgetManager.updateAppWidget(appWidgetId,views);

        }
    }


    public RemoteViews setRemoteViews(Context context, int widget_screen){
        int layout_id = R.layout.widget;
        if(widget_screen == 1)
            layout_id = R.layout.widget;
        else if(widget_screen == 2)
            layout_id = R.layout.widget2;
        return new RemoteViews(context.getPackageName(), layout_id);
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

        String tag = "tag";
        String WIDGET_ACTION = "android.action.WIDGET_BUTTON";

        // 수신한 인텐트로부터 데이터 읽기
        String action = intent.getAction();
        int tag_id = 0;
        if(action.equals(WIDGET_ACTION)){
            tag_id = intent.getIntExtra("tag",0);
        }

        Log.d(this.getClass().getName(),"widget onReceive() with tag_id " + tag_id);


        if(tag_id == 0)
            return;

        Log.d(this.getClass().getName(),"set view in onReceive()");
        widget_screen = 2;

        // remote view 생성
        RemoteViews views = setRemoteViews(context, widget_screen);

/*
        // tag_id: motive = 101, healing = 102, boring =103, refresh = 104
        if(action.equals(WIDGET_ACTION)){
            int tag_id = intent.getIntExtra("tag",0);


            switch(tag_id){
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

            // 텍스트 불러오기
            views.setTextViewText(R.id.widget_text, readData(tag));
*/
        views.setTextViewText(R.id.widget_text, "Hello "+tag);
        // 위젯 화면 갱신
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context,widget.class);
        appWidgetManager.updateAppWidget(cpName,views);
    }

    // 엑셀 파일에서 데이터 읽기 (작업중)
    public String readData(String tag){
        String content = "still working";
        try{
            FileInputStream file = new FileInputStream("data.xls");
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("0");
            int rows = sheet.getPhysicalNumberOfRows();
            String data_tag = null;

            do{
                int rowindex = (int)(Math.random()*rows);

            }while(!data_tag.equals(tag));

        }catch(java.io.IOException ex){
            System.out.println("IO exception is occured");
        }
        return content;
    }

}

