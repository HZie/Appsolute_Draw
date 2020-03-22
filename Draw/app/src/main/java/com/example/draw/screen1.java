package com.example.draw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class screen1 extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    RecyclerAdapter mAdapter = null ;
    ArrayList<RecyclerItem> mList = new ArrayList<>();
    TextView tv = null;

    private long memoPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable[] icons = {getDrawable(R.drawable.motivate),getDrawable(R.drawable.healing),getDrawable(R.drawable.boring),getDrawable(R.drawable.refresh)};
        mList = getDataList(icons);


        setContentView(R.layout.activity_screen1);

        Button add = findViewById(R.id.scrn01Btn_add);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(screen1.this,MainActivity.class);
            startActivity(intent);
        });

        tv = findViewById(R.id.scrn01_showMemo);
        tv.setVisibility(View.INVISIBLE);

        tv.setOnClickListener(v -> {

            if (System.currentTimeMillis() > memoPressedTime + 500) {
                memoPressedTime = System.currentTimeMillis();
                return;
            }
            if (System.currentTimeMillis() <= memoPressedTime + 500) {
                if(tv.getVisibility() == View.VISIBLE)
                    tv.setVisibility(View.INVISIBLE);
            }

        });

        mRecyclerView = findViewById(R.id.scrn01_recyclerView) ;

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerAdapter(mList) ;

        mAdapter.setOnDeleteBtnListener(new RecyclerAdapter.OnDeleteBtnListener() {
            @Override
            public void onDeleteBtnClick(View v, int pos) {
                if (tv.getVisibility() == View.INVISIBLE) {
                    if (pos != RecyclerView.NO_POSITION) {
                        // 데이터 리스트로부터 아이템 데이터 참조
                        RecyclerItem item = mList.get(pos);
                        SharedPreferences sf = v.getContext().getSharedPreferences("sFile", MODE_PRIVATE);

                        SharedPreferences.Editor editor = sf.edit();
                        if (sf.contains(item.getKey())) {
                            editor.remove(item.getKey());
                        }
                        editor.apply();

                        mAdapter.notifyDataSetChanged();
                        mList.remove(pos);
                        StringBuffer listKeys = new StringBuffer();

                        for (RecyclerItem value : mList) {
                            listKeys.append(value.getKey() + " ");
                        }

                        if (!mList.isEmpty()) {
                            sf.edit().putString("data_list", listKeys.toString()).commit();
                        } else {
                            sf.edit().putString("data_list", null).commit();
                        }
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if(pos != RecyclerView.NO_POSITION){
                    // 데이터 리스트로부터 아이템 데이터 참조
                    if(tv.getVisibility() == View.INVISIBLE) {
                        String key;
                        RecyclerItem item = mList.get(pos);

                        switch(item.getKey().charAt(0)){
                            case 'm':
                                key = "동기부여가 필요할 때\n------------------------------\n";
                                break;
                            case 'h':
                                key = "힐링이 필요할 때\n------------------------------\n";
                                break;
                            case 'b':
                                key = "지루할 때\n------------------------------\n";
                                break;
                            case 'r':
                                key = "기분전환이 필요할 때\n------------------------------\n";
                                break;
                            default:
                                key = "";
                                break;
                        }

                        tv.setVisibility(View.VISIBLE);
                        tv.setMovementMethod(new ScrollingMovementMethod());
                        tv.scrollTo(0,0);
                        tv.setText(key+item.getText());
                    }
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
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

    private long backPressedTime;

    // 뒤로가기 버튼 처리
    @Override
    public void onBackPressed() {

        if(backPressedTime == 0){
            Toast.makeText(screen1.this,"한 번 더 누르면 종료됩니다.",Toast.LENGTH_LONG).show();
            backPressedTime = System.currentTimeMillis();
        }
        else{
            int seconds = (int)(System.currentTimeMillis() - backPressedTime);

            if(seconds> 2000){
                backPressedTime = 0;
            }
            else{
                finish();
            }
        }
    }
}

