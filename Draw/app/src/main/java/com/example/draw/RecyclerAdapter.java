package com.example.draw;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> mData = null;
    // 생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerAdapter(ArrayList<RecyclerItem> list) {
        mData = list;
    }



    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIcon());
        holder.text.setText(item.getText());
        holder.button.setTag(holder.getAdapterPosition());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // view holder 생성
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            icon = itemView.findViewById(R.id.item_icon);
            text = itemView.findViewById(R.id.item_text);
            button = itemView.findViewById(R.id.itemBtn_delete);

            // 삭제 버튼 이벤트처리
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        // 데이터 리스트로부터 아이템 데이터 참조
                        RecyclerItem item = mData.get(pos);
                        SharedPreferences sf = v.getContext().getSharedPreferences("sFile",MODE_PRIVATE);

                        SharedPreferences.Editor editor = sf.edit();
                        if( sf.contains( item.getKey() ) ){ editor.remove( item.getKey() ); }
                        editor.apply();
                        notifyDataSetChanged();
                        mData.remove(pos);
                        StringBuffer listKeys = new StringBuffer();

                        for(RecyclerItem value: mData){
                            listKeys.append(value.getKey()+" ");
                        }

                        if(! mData.isEmpty()){
                            sf.edit().putString("data_list",listKeys.toString()).commit();
                        }
                        else{
                            sf.edit().putString("data_list",null).commit();
                        }
                    }
                }
            });

        }

    }




}