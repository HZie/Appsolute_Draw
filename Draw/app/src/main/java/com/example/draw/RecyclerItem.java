package com.example.draw;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private String dataKey;
    private Drawable iconView;
    private String textView;

    public RecyclerItem(){
        this.dataKey = null;
        this.iconView = null;
        this.textView = null;
    }

    public RecyclerItem( String dataKey, Drawable icon, String text){
        this.dataKey = dataKey;
        iconView = icon;
        textView = text;
    }

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

    public String getKey(){return this.dataKey;}

}
