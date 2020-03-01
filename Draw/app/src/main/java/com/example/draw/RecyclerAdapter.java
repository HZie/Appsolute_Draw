package com.example.draw;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view);

        return vh;
    }

    private Context mContext;
    SharedPreferences sf = mContext.getSharedPreferences("sFile", MODE_PRIVATE);

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIcon());
        holder.text.setText(item.getText());
        holder.button.setTag(holder.getAdapterPosition());
        holder.button.setOnClickListener(v -> {
            int pos = (int) v.getTag();
            mData.remove(pos);

            int mNum = Integer.parseInt(sf.getString("mNum","0"));
            int hNum = Integer.parseInt(sf.getString("hNum","0"));
            int bNum = Integer.parseInt(sf.getString("bNum","0"));
            int rNum = Integer.parseInt(sf.getString("rNum","0"));

            SharedPreferences.Editor editor = sf.edit();

            if(pos<=mNum){
                editor.remove("motivate"+pos);
                editor.commit();
            }
            else if(pos-mNum<= hNum) {
                int num = pos-mNum;
                editor.remove("healing"+num);
                editor.commit();
            }
            else if(pos-mNum-hNum<= bNum) {
                int num = pos-mNum-hNum;
                editor.remove("boring"+num);
                editor.commit();
            }
            else if(pos-mNum-hNum-bNum<= rNum) {
                int num = pos-mNum-hNum-bNum;
                editor.remove("refresh"+num);
                editor.commit();
            }

            notifyDataSetChanged();
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            icon = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            button = itemView.findViewById(R.id.button);
        }

    }
}