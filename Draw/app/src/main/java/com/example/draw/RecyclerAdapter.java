package com.example.draw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

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

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIcon());
        holder.text.setText(item.getText());
        holder.button.setOnClickListener(view -> {
            mData.remove(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), mData.size());
            try{
                FileInputStream file = new FileInputStream("data.xls");
                Workbook workbook = new HSSFWorkbook(file);
                Sheet sheet = workbook.getSheet("0");

                int rowCount = sheet.getLastRowNum();

                //데이터 덮어쓰기
                for(int i=holder.getAdapterPosition();i==rowCount-1;i++){
                    Row rowB = sheet.getRow(i);
                    Row row = sheet.getRow(i+1);
                    Cell cell1 = rowB.getCell(1) ;
                    Cell cell1A = row.getCell(1);
                    cell1.setCellValue((RichTextString) cell1A);
                    Cell cell2 = rowB.getCell(2);
                    Cell cell2A = row.getCell(2);
                    cell2.setCellValue((RichTextString) cell2A);
                }

                workbook.write(new FileOutputStream("data.xls"));

                workbook.close();
            }catch (java.io.IOException ex){

            }
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