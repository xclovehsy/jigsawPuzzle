package com.example.jigsawpuzzle;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Space {
    private int id;
    private ImageView view;
    private int x;
    private int y;
    private boolean isOccupy;

    public Space(int id, ImageView view, int x, int y) {
        this.id = id;
        this.view = view;
        this.x = x;
        this.y = y;
    }

    public Space() {
    }

    public int getId() {
        return id;
    }

    public ImageView getView() {
        return view;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOccupy() {
        return isOccupy;
    }

    public void setOccupy(boolean occupy) {
        isOccupy = occupy;
    }
}
