package com.example.draw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

=======
>>>>>>> 5002b97ff04f5c827f615dd62f61b598e7c3ff29
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

<<<<<<< HEAD
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

    public void bt4(View view)
    {
        fileOut("refresh",getStr());
    }


    public void fileOut(String tag,String content){
        //tag 와 입력문구를 입력받음
        //파일을 저장하고 다음 1번 screen로 넘어감.

        try{
            FileInputStream file = new FileInputStream("data.xls");
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet  = workbook.getSheet("0");

            //현재 존재하는 row 갯수
            int rowCount=sheet.getLastRowNum();
            //새로운 row추가 및 셀추가, 내용 추가.
            Row row = sheet.createRow(rowCount +1);
            Cell cell = row.createCell(0);
            cell.setCellValue(tag);
            cell = row.createCell(1);
            cell.setCellValue(content);


            try{File excelFile = new File(this.getFilesDir(),"data.xls");
                FileOutputStream os = new FileOutputStream(excelFile); workbook.write(os); }catch(IOException e){e.printStackTrace();}

            file.close();


        }
        catch(java.io.FileNotFoundException exc){

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
            row=sheet.createRow(1);
            cell=row.createCell(0);
            cell.setCellValue(tag);
            cell=row.createCell(1);
            cell.setCellValue(content);

            //파일 output
            try{File excelFile = new File(this.getFilesDir(),"data.xls");
            FileOutputStream os = new FileOutputStream(excelFile); workbook.write(os);}catch(IOException e){e.printStackTrace();}
        }

        catch(java.io.IOException ex){
            ex.printStackTrace();
        }



        //------------------------입력을 완수하고 다음 activity로 넘어간다.--------------------
        Intent intent = new Intent(this, MainActivity.class ); //넘겨드릴때 class이름이 1이시면 바꾸기!!
        startActivity(intent);

    }



=======


    }
>>>>>>> 5002b97ff04f5c827f615dd62f61b598e7c3ff29
}
