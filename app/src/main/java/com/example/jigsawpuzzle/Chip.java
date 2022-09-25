package com.example.jigsawpuzzle;

import android.widget.ImageView;

public class Chip {
    private int rightX;
    private int rightY;
    private int curX;
    private int curY;
    private MoveButton view;
    private int id;

    public Chip(int rightX, int rightY, MoveButton view, int id) {
        this.rightX = rightX;
        this.rightY = rightY;
        this.view = view;
        this.id = id;
        this.curX = -1;
        this.curY = -1;
    }

    public boolean isRightPosition(){
        return curX == rightX && curY == rightY;
    }

    public boolean isInSpace(){
        return !(curX == -1 && curY == -1);
    }

    public void outOfSpace(){
        curY = -1;
        curX = -1;
    }

    public Chip() {
    }

    public int getRightX() {
        return rightX;
    }

    public int getRightY() {
        return rightY;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public MoveButton getView() {
        return view;
    }

    public int getId() {
        return id;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public void setRightY(int rightY) {
        this.rightY = rightY;
    }

    public void setCurX(int curX) {
        this.curX = curX;
    }

    public void setCurY(int curY) {
        this.curY = curY;
    }

    public void setView(MoveButton view) {
        this.view = view;
    }

    public void setId(int id) {
        this.id = id;
    }
}
