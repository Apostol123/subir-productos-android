package com.example.subirproductosamitienda.model;

public class MyItem {
    private int icon;
    private String text;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MyItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }
}
