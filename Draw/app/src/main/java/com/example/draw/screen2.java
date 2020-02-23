package com.example.draw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class screen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

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


    public void fileOut(String tag, String content) {
        //tag 와 입력문구를 입력받음
        //파일을 저장하고 다음 1번 screen로 넘어감.

        try {

            FileInputStream file = new FileInputStream("data.xls");
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("0");

            //현재 존재하는 row 갯수
            int rowCount = sheet.getLastRowNum();
            //새로운 row추가 및 셀추가, 내용 추가.
            Row row = sheet.createRow(rowCount + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(tag);
            cell = row.createCell(1);
            cell.setCellValue(content);


            try {
                File excelFile = new File(this.getFilesDir(), "data.xls");
                FileOutputStream os = new FileOutputStream(excelFile);
                workbook.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }

            file.close();


        } catch (java.io.FileNotFoundException exc) {

            //----------------------------엑셀 파일 생성------------------------------------
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(); // 새로운 시트 생성
            Row row = sheet.createRow(0); // 새로운 행 생성
            Cell cell;
            //헤더
            // 1번 셀 생성과 입력
            cell = row.createCell(0);
            cell.setCellValue("tag");
            //2번 셀
            cell = row.createCell(1);
            cell.setCellValue("content");

            //새로운 행 생성과 데이터 입력
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue(tag);
            cell = row.createCell(1);
            cell.setCellValue(content);

            String filename="data.xls";
            File dir = getFilesDir();
            File xls = new File(dir,filename);

            //파일 output
            try {
                OutputStream os = new FileOutputStream(xls);
                workbook.write(os);
                Toast.makeText(getApplicationContext(),xls.getAbsolutePath()+"에 저장되었습니다",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }


        //------------------------입력을 완수하고 다음 activity로 넘어간다.--------------------
        Intent intent = new Intent(this, screen1.class); //넘겨드릴때 class이름이 1이시면 바꾸기!!
        startActivity(intent);

    }

    //sharedPreference 를 사용한 file output을 사용
    public void sharedOut(String tag,String content){
        // '저장된 각 항목 갯수' key를 motive=mNum boring=bNum healing=hNum refresh=rNum으로 설정
        //실제 내용은 key를 캐그이름+'그 태그 갯수+1' 로 하겠음.

        //저장될 태그의 지금까지 갯수
        int num = 0;

        //저장된 값을 불러오기위해 sFile을 찾음. 존재 안 할 수도 있음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        //만약 파일에 값이 없다면? num은 0으로 그냥 두기.
        try{

            switch(tag){
                case "motive": num = Integer.parseInt(sf.getString("mNum","0")); break;
                case "boring" : num = Integer.parseInt(sf.getString("bNum","0")); break;
                case "healing": num = Integer.parseInt(sf.getString("hNum","0")); break;
                case "refresh" : num = Integer.parseInt(sf.getString("rNum","0")); break;
            }
        }catch(Exception e){num=0;}

        //이제 저장할 항목은 num+1번째.
        num=num+1;

        //입력 내용 저장
        SharedPreferences.Editor editor = sf.edit();
        editor.putString( (tag+num) ,content);

        //tag + num 갯수 삭제 후 업데이트
        String tagNum= tag.substring(0, 1)+ "Num"; //mNum bNum hNum rNum
        editor.remove(tagNum);
        editor.putString(tagNum, Integer.toString(num) );
        editor.commit();


        //------------------------입력을 완수하고 다음 activity로 넘어간다.--------------------
        Intent intent = new Intent(this, screen1.class); //넘겨드릴때 class이름이 1이시면 바꾸기!!
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


}

