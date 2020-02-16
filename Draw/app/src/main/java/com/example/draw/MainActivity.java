package com.example.draw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
//클릭시 screen2 액티비티로 이동함
    public void onClick(View view)
    {

        //입력한 str 가져오기 -- screen 2에서 저장할 예정!
        EditText text = (EditText) findViewById(R.id.editText2);
        String str = text.getText().toString();

        //입력 내용이 없으면 toast로 띄우기. 아니면 인텐트 실행
        if(str == null ){
            Toast.makeText(this,"문구를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
        else{
            //screen2 액티비티에 전달
            Intent intent = new Intent(this, screen2.class );
            intent.putExtra("입력문구",str);
            startActivity(intent);
        }//end of else


    }
}
