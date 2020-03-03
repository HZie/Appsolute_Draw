package com.example.draw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class screen1 extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    RecyclerAdapter mAdapter = null ;
    ArrayList<RecyclerItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable[] icons = {getDrawable(R.drawable.icon1),getDrawable(R.drawable.icon2),getDrawable(R.drawable.icon3),getDrawable(R.drawable.icon4)};
        mList = getDataList(icons);

        setContentView(R.layout.activity_screen1);

        Button add = findViewById(R.id.scrn01Btn_add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(screen1.this,MainActivity.class);
            startActivity(intent);
        });

        mRecyclerView = findViewById(R.id.scrn01_recyclerView) ;

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerAdapter(mList) ;
<<<<<<< HEAD
        mRecyclerView.setAdapter(mAdapter);
=======
        mRecyclerView.setAdapter(mAdapter) ;

        // 파일 읽기
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        String context;
        Drawable image;

        int mNum = Integer.parseInt(sf.getString("mNum","0"));
        int hNum = Integer.parseInt(sf.getString("hNum","0"));
        int bNum = Integer.parseInt(sf.getString("bNum","0"));
        int rNum = Integer.parseInt(sf.getString("rNum","0"));

        // 아이템 추가
        if(mNum>0){
        for(int i=1; i==mNum; i++){
            image = getDrawable(R.drawable.icon1);
            context = sf.getString("motive"+i, null);
            addItem(image, context);
        }}

        if(hNum>0){
        for(int i=1; i==hNum; i++){
            image = getDrawable(R.drawable.icon2);
            context = sf.getString("healing"+i, null);
            addItem(image, context);
        }}

        if(bNum>0){
        for(int i=1; i==bNum; i++){
            image = getDrawable(R.drawable.icon3);
            context = sf.getString("boring"+i, null);
            addItem(image, context);
        }}

        if(rNum>0){
        for(int i=1; i==rNum; i++){
            image = getDrawable(R.drawable.icon4);
            context = sf.getString("refresh"+i, null);
            addItem(image, context);
        }}
>>>>>>> 92543f8fe2d55e6f87b2489b86169188456dc480

        mAdapter.notifyDataSetChanged();

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
            Log.e("Exception on screen1: ", e.toString());
        }
        return dataList;
    }


}

