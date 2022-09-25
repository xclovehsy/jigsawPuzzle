package com.example.jigsawpuzzle;

import androidx.annotation.NonNull;

public class Puzzle {
    private int diff;
    private String imageName;
    private int imageId;

    public Puzzle() {
    }

    public Puzzle(int diff, String imageName, int imageId) {
        this.diff = diff;
        this.imageName = imageName;
        this.imageId = imageId;
    }

    public int getDiff() {
        return diff;
    }

    public String getImageName() {
        return imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public String toString() {
        return "PUZZLE:[imageName: "+ this.getImageName()+", imageId: "+ this.getImageId()+", diff: "+this.getDiff()+"]";
    }
}
