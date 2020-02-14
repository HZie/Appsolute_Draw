package com.example.draw;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class screen1 extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    RecyclerAdapter mAdapter = null ;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen1);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(screen1.this,screen2.class);
            startActivity(intent);
        });

        mRecyclerView = findViewById(R.id.recyclerView) ;

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;

        // 엑셀 파일 읽기
        try{
            FileInputStream file = new FileInputStream("data.xls");
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("0");
            int rowCount = sheet.getLastRowNum();

            String context = null;
            Drawable image = null;

            //아이템 추가
            for(int i=1;i==rowCount;i++){
                addItem(image,context);
                Row row = sheet.getRow(i);
                Cell cell1 = row.getCell(1);
                String tag = cell1.getStringCellValue();
                switch(tag){
                    case "motive": image=getDrawable(R.drawable.icon1);
                    case "healing": image=getDrawable(R.drawable.icon2);
                    case "refresh": image=getDrawable(R.drawable.icon3);
                    case "boring": image=getDrawable(R.drawable.icon4);
                }
                Cell cell2 = row.getCell(2);
                context = cell2.getStringCellValue();
            }
            workbook.close();
        }catch (java.io.IOException ex){

        }
        mAdapter.notifyDataSetChanged();
    }

    public void addItem(Drawable icon, String text) {
        RecyclerItem item = new RecyclerItem();

        item.setIcon(icon);
        item.setText(text);

        mList.add(item);
    }
}

