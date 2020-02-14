package com.example.draw;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable iconView;
    private String textView;

    public void setIcon(Drawable icon){
        iconView=icon;
    }
    public void setText(String text){
        textView=text;
    }
    public Drawable getIcon(){
        return this.iconView;
    }
    public String getText(){
        return this.textView;
    }
}
