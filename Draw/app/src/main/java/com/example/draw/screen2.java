package com.example.draw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class screen2 extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);




        //-------------------------------------file output:이건 method로 빼서 버튼 클릭시 호출하게 변경-------------------------------
//        //--------genre나 str이 비어있으면 불가능하게 설정----------------------------------
//        try {
//
//            //FileOutputStream 객체생성, 파일명 "data.txt", 새로운 텍스트 추가하기 모드
//            FileOutputStream fos=openFileOutput("test.txt", Context.MODE_APPEND);
//            PrintWriter writer= new PrintWriter(fos);
//            writer.println(str);
//            writer.close();
//
//
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }//end of try-catch



    }


    public String getStr(){
        //-----------------------앞페이지에서 가져온 문구 읽기-----------------------------
        Intent intent = getIntent();
        String str = intent.getStringExtra("입력문구");
        return str;
    }

    public void bt1(View view){
        fileOut("motive",getStr());
    }

    public void bt2(View view){
        fileOut("healing",getStr());

    }

    public void bt3(View view){
        fileOut("boring",getStr());

    }

    public void bt4(View view){
        fileOut("refresh",getStr());
    }

    public void fileOut(genre,content){
        //genre 와 입력문구를 입력받음
        //파일을 저장하고 다음 1번 screen로 넘어감.

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
        //데이터 입력
        //파일 output



        //------------------------입력을 완수하고 다음 activity로 넘어간다.--------------------
        Intent intent = new Intent(this, MainActivity.class ); //넘겨드릴때 class이름이 1이시면 바꾸기!!
        startActivity(intent);

    }




}
