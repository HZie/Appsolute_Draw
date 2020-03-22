package com.example.draw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class screen2 extends AppCompatActivity {

    ArrayList<RecyclerItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        Drawable[] icons = {getDrawable(R.drawable.motivate),getDrawable(R.drawable.healing),getDrawable(R.drawable.boring),getDrawable(R.drawable.refresh)};
        mData = getDataList(icons);

        //Intent intent = getIntent();
        //String str = intent.getStringExtra("입력한 문구");
        //TextView statement = (TextView)findViewById(R.id.textView2);
        //intent로 가져온 텍스트로 설정
        //statement.setText(str);

    }


    public String getStr() {
        //-----------------------앞페이지에서 가져온 문구 읽기-----------------------------
        Intent intent = getIntent();
        String str = intent.getStringExtra("입력문구");
        return str;
    }

    public void bt1(View view) {
        sharedOut("motive", getStr());
    }

    public void bt2(View view) {
        sharedOut("healing", getStr());
    }

    public void bt3(View view) {
        sharedOut("boring", getStr());
    }

    public void bt4(View view) { sharedOut("refresh", getStr());
    }

    //sharedPreference 를 사용한 file output을 사용
    public void sharedOut(String tag,String content){
        // '저장된 각 항목 갯수' key를 motive=mNum boring=bNum healing=hNum refresh=rNum으로 설정
        //실제 내용은 key를 캐그이름+'그 태그 갯수+1' 로 하겠음.

        //저장될 태그의 지금까지 갯수
        int num = 0;

        //저장된 값을 불러오기위해 sFile을 찾음. 존재 안 할 수도 있음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        Drawable icon = null;
        //만약 파일에 값이 없다면? num은 0으로 그냥 두기.
        try{

            switch(tag){
                case "motive":
                    num = Integer.parseInt(sf.getString("mNum","0"));
                    icon = getDrawable(R.drawable.motivate);
                    break;
                case "boring" :
                    num = Integer.parseInt(sf.getString("bNum","0"));
                    icon = getDrawable(R.drawable.boring);
                    break;
                case "healing":
                    num = Integer.parseInt(sf.getString("hNum","0"));
                    icon = getDrawable(R.drawable.healing);
                    break;
                case "refresh" :
                    num = Integer.parseInt(sf.getString("rNum","0"));
                    icon = getDrawable(R.drawable.refresh);
                    break;
            }
        }catch(Exception e){num=0;}

        //이제 저장할 항목은 num+1번째.
        num=num+1;

        //입력 내용 저장
        SharedPreferences.Editor editor = sf.edit();
        editor.putString( (tag+num) ,content);
        RecyclerItem item = new RecyclerItem(tag+num,icon,content);
        mData.add(item);


        // mData를 shared preference에 저장
        saveDataList(mData);

        //tag + num 갯수 삭제 후 업데이트
        String tagNum= tag.substring(0, 1)+ "Num"; //mNum bNum hNum rNum
        editor.remove(tagNum);
        editor.putString(tagNum, Integer.toString(num) );
        editor.commit();


        //------------------------입력을 완수하고 다음 activity로 넘어간다.--------------------
        Intent intent = new Intent(this, screen1.class); //넘겨드릴때 class이름이 1이시면 바꾸기!!
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }//end of sharedOut


    //출력되는지 테스트용 함수
    public void getResult(){

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 "스트링 반환 실패"를 반환
        String text = sf.getString("motive1","스트링 반환 실패");
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

    }//end of getResult


    public void saveDataList(ArrayList<RecyclerItem> dataList){
        StringBuffer listKeys = new StringBuffer();
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        for(RecyclerItem value: dataList){
            listKeys.append(value.getKey()+" ");
        }

        if(! dataList.isEmpty()){
            sf.edit().putString("data_list",listKeys.toString()).commit();

        }
        else{
            sf.edit().putString("data_list",null).commit();
        }
    }

    // icons 0 = motive, 1 = healing, 2 = boring, 3 = refresh
   public ArrayList<RecyclerItem> getDataList(Drawable[] icons){
       ArrayList<RecyclerItem> dataList = new ArrayList<>();
       SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
       String listKeys = null;

       try{
           listKeys = sf.getString("data_list",null);
           if(listKeys != null){
               String[] keys = listKeys.split("\\s");

               for(String value: keys){
                   Drawable icon;

                   switch(value.charAt(0)){
                       case 'm':
                           icon = icons[0];
                           break;
                       case 'h':
                           icon = icons[1];
                           break;
                       case 'b':
                           icon = icons[2];
                           break;
                       case 'r':
                           icon = icons[3];
                           break;
                       default:
                           icon = null;
                           break;
                   }

                   RecyclerItem item = new RecyclerItem(value,icon,sf.getString(value,null));
                   dataList.add(item);
               }
           }
       }
       catch(Exception e){
           Log.e("Exception on screen2: ", e.toString());
       }
       return dataList;
   }

}

