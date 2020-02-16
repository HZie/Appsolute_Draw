package com.example.draw;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;


public class widget2 extends AppWidgetProvider {

    private static String tag = "";
    public static String WIDGET_ACTION = "FROM_WIDGET01";

    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);

        // remote view 생성
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget2);

        // 수신한 인텐트로부터 데이터 읽기
        String action = intent.getAction();


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

            // 위젯 화면 갱신
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName cpName = new ComponentName(context,widget2.class);
            appWidgetManager.updateAppWidget(cpName,views);
        }
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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context,widget2.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent,0);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);

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
